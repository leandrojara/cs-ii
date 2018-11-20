package br.ufms.facom.home.utils;

import br.ufms.facom.home.domain.enums.TipoFormato;
import br.ufms.facom.home.domain.enums.TipoTemplate;
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

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * home
 * TDR Informática Ltda
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: ReportUtils.java
 * Criado por : Leandro de Souza Jara
 * Data da criação : 25/10/2018
 * Observação :
 * ***********************************************
 */
public class ReportUtils {

    private static final String fileSeparator = System.getProperty("file.separator");
    public static String reportdir = System.getProperty("user.dir") + fileSeparator + "reportdir" + fileSeparator;
    public static String jasperdir = System.getProperty("user.dir") + fileSeparator + "jasperdir" + fileSeparator;

    public static String gerarRelatorio(TipoFormato formato, TipoTemplate tipoTemplate, String layout, List result, ReportParameter... reportParameters) {
        try {
            //criando o layout do relatorio a partir do jasper
            JasperDesign desenho = JRXmlLoader.load(jasperdir + layout);
            JasperReport relatorio = JasperCompileManager.compileReport(desenho);
            JRDataSource jrRS = new JRBeanCollectionDataSource(Arrays.asList(new ReportModel(result)));

            //setando os parametros
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("dataAtual", new Date());
            parameters.put("usuario", Utils.getUsuarioLogado() != null ? Utils.getUsuarioLogado().getNome() : "Usuário Anônimo");
            parameters.put("subreportDir", jasperdir);
            for (int i = 0; i < tipoTemplate.getOrdem().length; i++) {
                parameters.put("template" + i, tipoTemplate.getOrdem()[i]);
            }
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
                        default:
                            //nothing to do
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