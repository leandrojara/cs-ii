package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.ImovelImagem;
import br.ufms.facom.home.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;

public class ImagemImovelRepositoryTest {

    @Autowired
    private AnuncianteRepository anuncianteRepository;
    @Autowired
    private ImagemImovelRepository imagemImovelRepository;

    private Anunciante anunciante;

    @Before
    public void before() {
        anunciante = new Anunciante();
        anunciante.setNome("Leandro Jara");
        anunciante.setTelefone("(67)9999-9999");
        anunciante.setEmail("leandro@teste.com.br");
        anunciante.setSenha(Utils.encrypt("123"));

        Imovel imovel1 = new Imovel();
        imovel1.setDataCadastro(new Date());
        imovel1.setAnunciante(anunciante);
        imovel1.setDescricao("imovel 1");

        ImovelImagem imagem = new ImovelImagem();
        imagem.setImovel(imovel1);
        imagem.setDiretorio("test");

        imovel1.setImagens(new ArrayList<>());
        imovel1.getImagens().add(imagem);

        anunciante.setImoveis(new ArrayList<>());
        anunciante.getImoveis().add(imovel1);

        anuncianteRepository.save(anunciante);
    }

    @Test
    public void buscarPorImovel() {
        Assertions.assertThat(imagemImovelRepository.findByImovelId(anunciante.getImoveis().get(0).getId())).isNotNull();
        Assertions.assertThat(imagemImovelRepository.findByImovelId(anunciante.getImoveis().get(0).getId())).isNotEmpty();
        Assertions.assertThat(imagemImovelRepository.findByImovelId(anunciante.getImoveis().get(0).getId())).hasSize(1);
    }

    @After
    public void after() {
        anuncianteRepository.delete(anunciante);
    }
}
