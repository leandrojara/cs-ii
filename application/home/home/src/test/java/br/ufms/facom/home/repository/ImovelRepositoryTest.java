package br.ufms.facom.home.repository;

import br.ufms.facom.home.HomeApplicationTests;
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

public class ImovelRepositoryTest extends HomeApplicationTests {

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
        imovel1.setRua("rua domingo legal");
        imovel1.setBairro("bairro jockey");
        imovel1.setCidade("cidade morta");
        imovel1.setEstado("PE");

        anunciante.setImoveis(new ArrayList<>());
        anunciante.getImoveis().add(imovel1);

        anuncianteRepository.save(anunciante);

        Imovel imovel2 = new Imovel();
        imovel2.setDataCadastro(new Date());
        imovel2.setAnunciante(anunciante);
        imovel2.setDescricao("imovel 2");
        imovel2.setRua("rua domingo legal");
        imovel2.setBairro("bairro vila");
        imovel2.setCidade("cidade viva");
        imovel2.setEstado("MS");

        imovelRepository.save(imovel2);
    }

    @Test
    public void buscarPorAnuncianteTest() {
        Assertions.assertThat(imovelRepository.findByAnuncianteId(anunciante.getId())).isNotNull();
        Assertions.assertThat(imovelRepository.findByAnuncianteId(anunciante.getId())).isNotEmpty();
        Assertions.assertThat(imovelRepository.findByAnuncianteId(anunciante.getId())).hasSize(2);
    }

    @Test
    public void buscarPorParametrosTest() {
        Assertions.assertThat(imovelRepository.findByParameters("rua", null, null, null, null, null).getTotalElements()).isGreaterThan(0L);
        Assertions.assertThat(imovelRepository.findByParameters("RUA", null, null, null, null, null).getTotalElements()).isGreaterThan(0L);
        Assertions.assertThat(imovelRepository.findByParameters("LEGAL", null, null, null, null, null).getTotalElements()).isGreaterThan(0L);
    }

    @Test
    public void buscarRuasTest(){
        Assertions.assertThat(imovelRepository.findRuas("rua")).hasSize(1);
        Assertions.assertThat(imovelRepository.findRuas("RUA")).hasSize(1);
    }

    @Test
    public void buscarBairrosTest(){
        Assertions.assertThat(imovelRepository.findBairros("bairro")).hasSize(2);
        Assertions.assertThat(imovelRepository.findBairros("BAIRRO")).hasSize(2);
    }

    @Test
    public void buscarCidadesTest(){
        Assertions.assertThat(imovelRepository.findCidades("cidade")).hasSize(2);
        Assertions.assertThat(imovelRepository.findCidades("CIDADE")).hasSize(2);
    }

    @After
    public void after() {
        anuncianteRepository.deleteAll();
    }
}
