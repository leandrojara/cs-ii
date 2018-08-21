package br.ufms.facom.home.domain.enums;

public enum TipoConservacao {

    NOVO("Novo"),
    USADO("Usado"),
    EM_CONSTRUCAO("Em construção"),
    PLANTA("Na planta");

    private final String descricao;

    TipoConservacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
