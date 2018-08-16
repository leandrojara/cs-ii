package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.repository.UsuarioRepository;
import br.ufms.facom.home.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AnuncianteRepository anuncianteRepository;

    @PostMapping(value = "/login/{email}/{senha}")
    public Usuario login(@PathVariable String email, @PathVariable String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().getSenha().equals(Utils.encrypt(senha))) {
            return usuario.get();
        }
        return null;
    }

    @PostMapping(value = "/salvar")
    public Usuario salvar(@RequestBody Usuario usuario) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(usuario.getEmail());
        if (optionalUsuario.isPresent()) {
            throw new Exception("Já existe um usuário com o email informado.");
        }

        if (usuario instanceof Anunciante) {
            anuncianteRepository.save((Anunciante) usuario);
        } else {
            usuarioRepository.save(usuario);
        }

        return usuario;
    }
}
