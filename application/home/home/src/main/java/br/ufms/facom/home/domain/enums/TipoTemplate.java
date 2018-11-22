package br.ufms.facom.home.domain.enums;

/**
 * home
 * TDR Informática Ltda
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: TipoTemplate.java
 * Criado por : Leandro de Souza Jara
 * Data da criação : 25/10/2018
 * Observação :
 * ***********************************************
 */
public enum TipoTemplate {

    CABECALHO_CORPO_RODAPE("Cabeçalho", "Corpo", "Rodapé"),
    RODAPE_CABECALHO_CORPO("Rodapé", "Cabeçalho", "Corpo"),
    CABECALHO_RODAPE("Cabeçalho", "Rodapé");

    private final String descricao;
    private final String[] ordem;

    TipoTemplate(String... ordem) {
        this.ordem = ordem;

        StringBuilder descricao = new StringBuilder();
        for (int i = 0; i < ordem.length; i++) {
            if (i > 0) {
                descricao.append(", ");
            }
            descricao.append(ordem[i]);
        }
        this.descricao = descricao.toString();
    }

    public String getDescricao() {
        return descricao;
    }

    public String[] getOrdem() {
        return ordem;
    }
}
