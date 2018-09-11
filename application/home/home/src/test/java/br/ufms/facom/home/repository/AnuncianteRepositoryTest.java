package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AnuncianteRepositoryTest {

    @Autowired
    private AnuncianteRepository anuncianteRepository;

    private Anunciante anunciante;

    @Before
    public void before() {
        anunciante = new Anunciante();
        anunciante.setNome("Leandro Jara");
        anunciante.setTelefone("(67)9999-9999");
        anunciante.setEmail("leandro@teste.com.br");
        anunciante.setSenha(Utils.encrypt("123"));
        anuncianteRepository.save(anunciante);
    }

    @Test
    public void updateAnuncianteTest() {
        anunciante.setCnpj("123456789");
        anuncianteRepository.save(anunciante);
        Assertions.assertThat(anuncianteRepository.findById(anunciante.getId()).get().getCnpj()).isEqualTo("123456789");
    }

    @Test
    public void buscarPorIdTest() {
        Assertions.assertThat(anuncianteRepository.findById(anunciante.getId()).get()).isNotNull();
    }

    @Test
    public void buscarPorEmailTest() {
        Assertions.assertThat(anuncianteRepository.findByEmail("leandro@teste.com.br")).isNotNull();
    }

    @Test
    public void buscarTodosTest() {
        Assertions.assertThat(anuncianteRepository.findAll()).isNotNull();
        Assertions.assertThat(anuncianteRepository.findAll()).isNotEmpty();
    }

    @After
    public void after() {
        anuncianteRepository.delete(anunciante);
    }
}
