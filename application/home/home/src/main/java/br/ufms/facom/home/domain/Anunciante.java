package br.ufms.facom.home.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "anunciante")
public class Anunciante extends Usuario {

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "creci")
    private String creci;

    @OneToMany(mappedBy = "anunciante", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Imovel> imoveis = new ArrayList<>();

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

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }
}
