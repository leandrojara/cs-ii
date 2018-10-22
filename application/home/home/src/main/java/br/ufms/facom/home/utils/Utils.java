package br.ufms.facom.home.utils;

import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.domain.bean.Validation;
import br.ufms.facom.home.domain.enums.TipoFormato;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
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
    public static String reportdir = System.getProperty("user.dir") + fileSeparator + "reportdir" + fileSeparator;
    public static String jasperdir = System.getProperty("user.dir") + fileSeparator + "jasperdir" + fileSeparator;

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

            //gerando o arquivo
            JasperPrint jasperPrint = JasperFillManager.fillReport(relatorio, parameters, jrRS);
            File outDir = new File(reportdir);
            outDir.mkdirs();

            String nomeArquivo = new Date().getTime() + formato.getExtensao();
            switch (formato) {
                case JSON:
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(new File(reportdir + nomeArquivo), result);
                    break;
                case XML:
                    XmlMapper xmlMapper = new XmlMapper();
                    xmlMapper.writeValue(new File(reportdir + nomeArquivo), result);
                    break;
                default:
                    JRAbstractExporter exporter = formato.getClazz().newInstance();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportdir + nomeArquivo);
                    switch (formato) {
                        case EXCEL:
                            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                            break;
                        case TXT:
                            exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 150);
                            exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 60);
                            break;
                    }
                    exporter.exportReport();
                    break;
            }

            //retorna o caminho do relatorio gerado
            return reportdir + nomeArquivo;
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
