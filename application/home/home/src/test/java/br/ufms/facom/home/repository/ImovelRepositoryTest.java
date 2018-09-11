package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;

public class ImovelRepositoryTest {

    @Autowired
    private AnuncianteRepository anuncianteRepository;
    @Autowired
    private ImovelRepository imovelRepository;

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

        anunciante.setImoveis(new ArrayList<>());
        anunciante.getImoveis().add(imovel1);

        anuncianteRepository.save(anunciante);

        Imovel imovel2 = new Imovel();
        imovel2.setDataCadastro(new Date());
        imovel2.setAnunciante(anunciante);
        imovel2.setDescricao("imovel 2");

        imovelRepository.save(imovel2);
    }

    @Test
    public void buscarPorAnuncianteTest() {
        Assertions.assertThat(imovelRepository.findByAnuncianteId(anunciante.getId())).isNotNull();
        Assertions.assertThat(imovelRepository.findByAnuncianteId(anunciante.getId())).isNotEmpty();
        Assertions.assertThat(imovelRepository.findByAnuncianteId(anunciante.getId())).hasSize(2);
    }

    @After
    public void after() {
        anuncianteRepository.delete(anunciante);
    }
}
