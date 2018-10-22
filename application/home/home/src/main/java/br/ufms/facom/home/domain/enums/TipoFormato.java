package br.ufms.facom.home.domain.enums;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import org.springframework.http.MediaType;

public enum TipoFormato {

    PDF("PDF", ".pdf", MediaType.APPLICATION_PDF_VALUE),
    HTML("HTML", ".html", MediaType.TEXT_HTML_VALUE),
    XML("XML", ".xml", MediaType.APPLICATION_XML_VALUE),
    JSON("JSON", ".json", MediaType.APPLICATION_JSON_VALUE),
    TXT("TXT", ".txt", MediaType.TEXT_PLAIN_VALUE, JRTextExporter.class),
    CSV("CSV", ".csv", "text/csv", JRCsvExporter.class),
    ODT("ODT", ".odt", "application/vnd.oasis.opendocument.text", JROdtExporter.class),
    EXCEL("EXCEL", ".xls", "application/vnd.ms-excel", JRXlsExporter.class);

    private final String descricao;
    private final String extensao;
    private final String mediaType;
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
