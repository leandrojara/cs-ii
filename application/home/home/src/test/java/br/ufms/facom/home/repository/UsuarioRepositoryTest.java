package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @Before
    public void before() {
        usuario = new Usuario();
        usuario.setNome("Leandro Jara");
        usuario.setTelefone("(67)9999-9999");
        usuario.setEmail("leandro@teste.com.br");
        usuario.setSenha(Utils.encrypt("123"));
        usuarioRepository.save(usuario);
    }

    @Test
    public void updateAnuncianteTest() {
        usuario.setSenha(Utils.encrypt("12345"));
        usuarioRepository.save(usuario);
        Assertions.assertThat(usuarioRepository.findById(usuario.getId()).get().getSenha()).isEqualTo(Utils.encrypt("12345"));
    }

    @Test
    public void buscarPorIdTest() {
        Assertions.assertThat(usuarioRepository.findById(usuario.getId()).get()).isNotNull();
    }

    @Test
    public void buscarPorEmailTest() {
        Assertions.assertThat(usuarioRepository.findByEmail("leandro@teste.com.br")).isNotNull();
    }

    @Test
    public void buscarTodosTest() {
        Assertions.assertThat(usuarioRepository.findAll()).isNotNull();
        Assertions.assertThat(usuarioRepository.findAll()).isNotEmpty();
    }

    @After
    public void after() {
        usuarioRepository.delete(usuario);
    }
}
