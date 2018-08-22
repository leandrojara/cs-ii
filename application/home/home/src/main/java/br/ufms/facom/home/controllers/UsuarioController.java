package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.repository.ImovelRepository;
import br.ufms.facom.home.repository.UsuarioRepository;
import br.ufms.facom.home.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioController implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ImovelRepository imovelRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            List<GrantedAuthority> grantList = new ArrayList<>();
            GrantedAuthority authority = new SimpleGrantedAuthority(usuario.get().getId().toString());
            grantList.add(authority);

            UserDetails userDetails = new Usuario(usuario.get().getNome(), usuario.get().getSenha(), grantList);
            return userDetails;
        } else {
            throw new UsernameNotFoundException("Usuário não cadastrado no sistema.");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String redirectLogin() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("usuarioLogado", Utils.getUsuarioLogado());
        model.addAttribute("imoveis", imovelRepository.findAll());
        return "index";
    }
}
