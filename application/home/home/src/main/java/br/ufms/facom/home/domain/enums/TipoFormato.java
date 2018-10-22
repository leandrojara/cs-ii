package br.ufms.facom.home.domain.enums;

import org.springframework.http.MediaType;

public enum TipoFormato {

    PDF("PDF", ".pdf", MediaType.APPLICATION_PDF),
    HTML("HTML", ".html", MediaType.TEXT_HTML),
    XML("XML", ".xml", MediaType.APPLICATION_XML),
    EXCEL("EXCEL", ".xls", new MediaType("application/vnd.ms-excel"));

    private final String descricao;
    private final String extensao;
    private final MediaType mediaType;

    TipoFormato(String descricao, String extensao, MediaType mediaType) {
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

    public MediaType getMediaType() {
        return mediaType;
    }
}
