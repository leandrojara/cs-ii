package br.ufms.facom.home.domain.enums;

import org.springframework.http.MediaType;

public enum TipoFormato {

    PDF("PDF", ".pdf", MediaType.APPLICATION_PDF_VALUE),
    HTML("HTML", ".html", MediaType.TEXT_HTML_VALUE),
    XML("XML", ".xml", MediaType.APPLICATION_XML_VALUE),
    TXT("TXT", ".txt", MediaType.TEXT_PLAIN_VALUE),
    CSV("CSV", ".csv", "text/csv"),
    JSON("JSON", ".json", MediaType.APPLICATION_JSON_VALUE),
    EXCEL("EXCEL", ".xls", "application/vnd.ms-excel");

    private final String descricao;
    private final String extensao;
    private final String mediaType;

    TipoFormato(String descricao, String extensao, String mediaType) {
        this.descricao = descricao;
        this.extensao = extensao;
        this.mediaType = mediaType;
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
}
