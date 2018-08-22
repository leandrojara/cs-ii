package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioController implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            List<GrantedAuthority> grantList = new ArrayList<>();
            GrantedAuthority authority = new SimpleGrantedAuthority("user");
            grantList.add(authority);

            UserDetails userDetails = new Usuario(usuario.get().getNome(), usuario.get().getSenha(), grantList);
            return userDetails;
        } else {
            System.out.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
    }

    @RequestMapping(value = "/login/{email}/{senha}", method = RequestMethod.POST)
    public String login(@PathVariable("email") String email, @PathVariable("senha") String senha, Model model) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (!usuario.isPresent()) {
            model.addAttribute("erro", "Usuário não cadastrado no sistema");
            return "login";
        }

        if (senha.equals(usuario.get().getSenha())) {
            model.addAttribute("usuarioLogado", usuario);
            return "index";
        } else {
            model.addAttribute("erro", "Usuário e/ou senha inválidos");
            return "login";
        }
    }
}
