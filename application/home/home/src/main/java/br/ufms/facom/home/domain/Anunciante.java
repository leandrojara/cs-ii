package br.ufms.facom.home.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "anunciante")
public class Anunciante extends Usuario {

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "creci")
    private String creci;

    public Anunciante() {
    }

    public Anunciante(Long id) {
        setId(id);
    }

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
