package br.ufms.facom.home.domain.enums;

/**
 * home
 * TDR Informática Ltda
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: TipoRelatorio.java
 * Criado por : Leandro de Souza Jara
 * Data da criação : 22/10/2018
 * Observação :
 * ***********************************************
 */
public enum TipoRelatorio {

    LISTAGEM_VENDAS("Listagem de Imóveis para Venda", "listagemImoveis.jrxml"),
    LISTAGEM_ALUGUEL("Listagem de Imóveis para Aluguel", "listagemImoveis.jrxml");

    private final String descricao;
    private final String jrxml;

    TipoRelatorio(String descricao, String jrxml) {
        this.descricao = descricao;
        this.jrxml = jrxml;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getJrxml() {
        return jrxml;
    }
}
