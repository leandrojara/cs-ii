package br.ufms.facom.home.domain.enums;

public enum  TipoImovel {

    CASA("Casa"),
    APARTAMENTO("Apartamento"),
    TERRENO("Terreno"),
    SOBRADO("Sobrado"),
    COMERCIAL("Comercial"),
    RURAL("Rural");

    private final String descricao;

    TipoImovel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
