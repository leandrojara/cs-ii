package br.ufms.facom.home.utils;

import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.domain.bean.Validation;
import br.ufms.facom.home.domain.enums.TipoFormato;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.ObjectError;

import java.io.File;
import java.io.IOException;
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

    public static String gerarRelatorio(TipoFormato formato, String layout, List result, ReportParameter... reportParameters) {
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

            String nomeArquivo = new Date().getTime() + formato.getExtensao();
            switch (formato) {
                case PDF:
                    JasperExportManager.exportReportToPdfFile(jasperPrint, reportdir + nomeArquivo);
                    break;
                case XML:
//                    JRXmlExporter exporterXml = new JRXmlExporter();
                    JasperExportManager.exportReportToXmlFile(jasperPrint, reportdir + nomeArquivo, false);
                    break;
                case HTML:
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, reportdir + nomeArquivo);
                    break;
                case EXCEL:
                    JRXlsExporter exporterExcel = new JRXlsExporter();
                    exporterExcel.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                    exporterExcel.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                    exporterExcel.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, reportdir + nomeArquivo);
                    exporterExcel.exportReport();
                    break;
                case CSV:
                    JRCsvExporter exporterCsv = new JRCsvExporter();
                    exporterCsv.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporterCsv.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportdir + nomeArquivo);
                    exporterCsv.exportReport();
                    break;
                case TXT:
                    JRTextExporter exporterTxt = new JRTextExporter();
                    exporterTxt.setParameter(JRTextExporterParameter.PAGE_WIDTH, 150);
                    exporterTxt.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 60);
                    exporterTxt.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporterTxt.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportdir + nomeArquivo);
                    exporterTxt.exportReport();
                    break;
                case JSON:
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.writeValue(new File(reportdir + nomeArquivo), result);
                    } catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            //retorna o caminho do relatorio gerado
            return reportdir + nomeArquivo;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }
}
