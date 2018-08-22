package br.ufms.facom.home.domain;

import javax.persistence.*;

@Entity
@Table(name = "adicional_imovel")
public class AdicionalImovel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    public AdicionalImovel() {
    }

    public AdicionalImovel(Long id) {
        setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
