package br.ufms.facom.home.domain.enums;

public enum TipoFormato {

    PDF("pdf"),
    HTML("html"),
    XML("xml"),
    EXCEL("xls");

    private final String descricao;

    TipoFormato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
