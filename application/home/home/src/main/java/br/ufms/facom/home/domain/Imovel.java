package br.ufms.facom.home.domain;

import br.ufms.facom.home.domain.enums.TipoConservacao;
import br.ufms.facom.home.domain.enums.TipoImovel;
import br.ufms.facom.home.domain.enums.TipoNegocio;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "imovel")
public class Imovel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro")
    private Date dataCadastro;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anunciante", referencedColumnName = "id")
    private Anunciante anunciante;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "rua")
    private String rua;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "numero")
    private String numero;

    @Column(name = "cep")
    private String cep;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "tipo_imovel")
    @Enumerated(EnumType.STRING)
    private TipoImovel tipoImovel;

    @Column(name = "tipo_negocio")
    @Enumerated(EnumType.STRING)
    private TipoNegocio tipoNegocio;

    @Column(name = "tipo_conservacao")
    @Enumerated(EnumType.STRING)
    private TipoConservacao tipoConservacao;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "valor_imovel")
    private Double valorImovel;

    @Column(name = "valor_condominio")
    private Double valorCondominio;

    @Column(name = "largura")
    private Double largura;

    @Column(name = "comprimento")
    private Double comprimento;

    @Column(name = "area_total")
    private Double areaTotal;

    @Column(name = "area_construida")
    private Double areaConstruida;

    @Column(name = "comodos")
    private Integer comodos;

    @Column(name = "quartos_simples")
    private Integer quartosSimples;

    @Column(name = "banheiros_simples")
    private Integer banheirosSimples;

    @Column(name = "suites")
    private Integer suites;

    @Column(name = "vagasGaragem")
    private Integer vagasGaragem;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "join_imovel_adicional",
            joinColumns = {@JoinColumn(name = "id_imovel")},
            inverseJoinColumns = {@JoinColumn(name = "id_adicional")})
    private List<AdicionalImovel> adicionais;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "imovel")
    private List<ImovelImagem> imagens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Anunciante getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(Anunciante anunciante) {
        this.anunciante = anunciante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public TipoImovel getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(TipoImovel tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public TipoNegocio getTipoNegocio() {
        return tipoNegocio;
    }

    public void setTipoNegocio(TipoNegocio tipoNegocio) {
        this.tipoNegocio = tipoNegocio;
    }

    public TipoConservacao getTipoConservacao() {
        return tipoConservacao;
    }

    public void setTipoConservacao(TipoConservacao tipoConservacao) {
        this.tipoConservacao = tipoConservacao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getValorImovel() {
        return valorImovel;
    }

    public void setValorImovel(Double valorImovel) {
        this.valorImovel = valorImovel;
    }

    public Double getValorCondominio() {
        return valorCondominio;
    }

    public void setValorCondominio(Double valorCondominio) {
        this.valorCondominio = valorCondominio;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }

    public Double getAreaConstruida() {
        return areaConstruida;
    }

    public void setAreaConstruida(Double areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

    public Integer getComodos() {
        return comodos;
    }

    public void setComodos(Integer comodos) {
        this.comodos = comodos;
    }

    public Integer getQuartosSimples() {
        return quartosSimples;
    }

    public void setQuartosSimples(Integer quartosSimples) {
        this.quartosSimples = quartosSimples;
    }

    public Integer getBanheirosSimples() {
        return banheirosSimples;
    }

    public void setBanheirosSimples(Integer banheirosSimples) {
        this.banheirosSimples = banheirosSimples;
    }

    public Integer getSuites() {
        return suites;
    }

    public void setSuites(Integer suites) {
        this.suites = suites;
    }

    public Integer getVagasGaragem() {
        return vagasGaragem;
    }

    public void setVagasGaragem(Integer vagasGaragem) {
        this.vagasGaragem = vagasGaragem;
    }

    public List<AdicionalImovel> getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(List<AdicionalImovel> adicionais) {
        this.adicionais = adicionais;
    }

    public Double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Double areaTotal) {
        this.areaTotal = areaTotal;
    }

    public List<ImovelImagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImovelImagem> imagens) {
        this.imagens = imagens;
    }

    public String getDescription(){
        return tipoImovel.getDescricao() + ", " + cidade + ", " + bairro;
    }

    public String getValorStr(){
        return NumberFormat.getCurrencyInstance().format(valorImovel);
    }
}
