package br.ufms.facom.home.domain.enums;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import org.springframework.http.MediaType;

public enum TipoFormato {

    PDF("PDF", ".pdf", MediaType.APPLICATION_PDF_VALUE, JRPdfExporter.class),
    HTML("HTML", ".html", MediaType.TEXT_HTML_VALUE, HtmlExporter.class),
    XML("XML", ".xml", MediaType.APPLICATION_XML_VALUE),
    JSON("JSON", ".json", MediaType.APPLICATION_JSON_VALUE),
    TXT("TXT", ".txt", MediaType.TEXT_PLAIN_VALUE, JRTextExporter.class),
    CSV("CSV", ".csv", "text/csv", JRCsvExporter.class),
    ODT("ODT", ".odt", "application/vnd.oasis.opendocument.text", JROdtExporter.class),
    XHTML("XHTML", ".xhtml", MediaType.APPLICATION_XHTML_XML_VALUE, JRXhtmlExporter.class),
    EXCEL("EXCEL", ".xls", "application/vnd.ms-excel", JRXlsExporter.class);

    //Obrigatório: Nome conhecido do formato a nível de usuário
    private final String descricao;
    //Obrigatório: Extensão do arquivo a ser gerado
    private final String extensao;
    //Obrigatório: MediaType do formato a nível HTTP, para enviar no Header da  requisição
    private final String mediaType;
    //Opcional: Classe responsável por converter o relatório para a extensão desejada
    private final Class clazz;

    TipoFormato(String descricao, String extensao, String mediaType, Class clazz) {
        this.descricao = descricao;
        this.extensao = extensao;
        this.mediaType = mediaType;
        this.clazz = clazz;
    }

    TipoFormato(String descricao, String extensao, String mediaType) {
        this(descricao, extensao, mediaType, null);
    }

    public String getDescricao() {
        return descricao;
    }

    public String getExtensao() {
        return extensao;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Class<JRAbstractExporter> getClazz() {
        return clazz;
    }
}
