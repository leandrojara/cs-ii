package br.ufms.facom.home.domain.enums;

public enum TipoFormato {

    PDF("PDF"),
    HTML("HTML"),
    XML("XML"),
    EXCEL("EXCEL");

    private final String descricao;

    TipoFormato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
