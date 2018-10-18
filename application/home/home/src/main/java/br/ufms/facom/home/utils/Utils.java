package br.ufms.facom.home.utils;

import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.domain.bean.Validation;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.ObjectError;

import java.io.File;
import java.util.*;

public class Utils {

    private static final String fileSeparator = System.getProperty("file.separator");
    private static final String reportdir = System.getProperty("user.dir") + fileSeparator + "reportdir" + fileSeparator;
    private static final String jasperdir = System.getProperty("user.dir") + fileSeparator + "jasperdir" + fileSeparator;

    public static BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(7);
    }

    public static String encrypt(String value) {
        return getEncoder().encode(value);
    }

    public static List<Validation> criarListaDeErrosDaValidacao(List<ObjectError> erros) {
        List<Validation> lista = new ArrayList<>();
        if (erros != null && !erros.isEmpty()) {
            for (ObjectError error : erros) {
                lista.add(new Validation(error.getObjectName(), error.getDefaultMessage()));
            }
        }
        return lista;
    }

    public static Usuario getUsuarioLogado() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object obj = authentication.getPrincipal();
            if (obj instanceof Usuario) {
                return (Usuario) obj;
            }
        }
        return null;
    }

    public static String gerarRelatorio(String formato, String layout, List result, ReportParameter... reportParameters) {
        try {
            //criando o layout do relatorio a partir do jasper
            JasperDesign desenho = JRXmlLoader.load(jasperdir + layout);
            JasperReport relatorio = JasperCompileManager.compileReport(desenho);
            JRDataSource jrRS = new JRBeanCollectionDataSource(result);

            //setando os parametros
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("dataAtual", new Date());
            parameters.put("usuario", getUsuarioLogado() != null ? getUsuarioLogado().getNome() : "Usuário Anônimo");
            if (reportParameters != null) {
                for (ReportParameter parameter : reportParameters) {
                    parameters.put(parameter.getKey(), parameter.getValue());
                }
            }

            //gerando o pdf
            JasperPrint jasperPrint = JasperFillManager.fillReport(relatorio, parameters, jrRS);
            File outDir = new File(reportdir);
            outDir.mkdirs();

            String nomeArquivo = new String();

            if (formato == "pdf") {
                nomeArquivo = new Date().getTime() + ".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, reportdir + nomeArquivo);
            } else if (formato == "xml") {
                nomeArquivo = new Date().getTime() + ".xml";
                JasperExportManager.exportReportToXmlFile(jasperPrint, reportdir + nomeArquivo, true);
            } else if (formato == "html") {
                nomeArquivo = new Date().getTime() + ".html";
                JasperExportManager.exportReportToHtmlFile(jasperPrint, reportdir + nomeArquivo);
            } else if (formato == "xls") {
                JRXlsExporter exporter = new JRXlsExporter();

                exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
                        jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                        new Date().getTime() + ".xls");

                exporter.exportReport();
            }

            //retorna o caminho do relatorio gerado
            return reportdir + nomeArquivo;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }
}
