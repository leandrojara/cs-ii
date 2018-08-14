package br.ufms.facom.home.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "anunciante")
public class Anunciante extends Usuario {

    @Column(name = "cnpj")
    private String cnpj;

    @NotNull(message = "O CRECI do usuário não foi informado!")
    @NotEmpty(message = "O CRECI do usuário não foi informado!")
    @Column(name = "creci")
    private String creci;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCreci() {
        return creci;
    }

    public void setCreci(String creci) {
        this.creci = creci;
    }
}
