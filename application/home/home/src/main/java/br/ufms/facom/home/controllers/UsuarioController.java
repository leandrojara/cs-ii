package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Usuario;
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

    @PostMapping(name = "/login")
    public Usuario login(@PathVariable String email, @PathVariable String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().getSenha().equals(Utils.encrypt(senha))) {
            return usuario.get();
        }
        return null;
    }
}
