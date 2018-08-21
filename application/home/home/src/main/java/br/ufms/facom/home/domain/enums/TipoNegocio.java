package br.ufms.facom.home.domain.enums;

public enum TipoNegocio {

    VENDA("Venda"),
    ALUGUEL("Aluguel");

    private final String descricao;

    TipoNegocio(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
