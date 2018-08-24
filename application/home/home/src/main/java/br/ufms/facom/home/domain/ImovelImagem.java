package br.ufms.facom.home.domain;

import javax.persistence.*;
import java.util.Base64;

@Entity
@Table(name = "imovel_imagem")
public class ImovelImagem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_imovel", referencedColumnName = "id")
    private Imovel imovel;

    @Column(name = "diretorio")
    private String diretorio;

    @Transient
    private byte[] bytesImg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public byte[] getBytesImg() {
        return bytesImg;
    }

    public void setBytesImg(byte[] bytesImg) {
        this.bytesImg = bytesImg;
    }

    public String getBytesImgFormatted() {
        String tipoImagem = diretorio.split("\\.")[diretorio.split("\\.").length - 1];
        return "data:image/" + tipoImagem + ";base64," + new String(Base64.getEncoder().encode(bytesImg));
    }
}
