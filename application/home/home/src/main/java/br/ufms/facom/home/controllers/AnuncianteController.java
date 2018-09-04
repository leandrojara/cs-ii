package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.repository.UsuarioRepository;
import br.ufms.facom.home.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AnuncianteController {

    @Autowired
    private AnuncianteRepository anuncianteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping(value = "/anunciante/cadastrar", method = RequestMethod.GET)
    public String cadastrarAnunciante(Model model) {
        model.addAttribute("anunciante", new Anunciante());
        return "anunciante/cadastrar";
    }

    @RequestMapping(value = "/anunciante/editar/", method = RequestMethod.GET)
    public String editarAnunciante(Model model) {
        model.addAttribute("anunciante", anuncianteRepository.findById(Utils.getUsuarioLogado().getId()).get());
        return "anunciante/editar";
    }

    @RequestMapping(value = "/anunciante/excluir/", method = RequestMethod.GET)
    public String excluir() {
        anuncianteRepository.deleteById(Utils.getUsuarioLogado().getId());
        return "login";
    }

    @RequestMapping(value = "/anunciante/salvar", method = RequestMethod.POST)
    public String salvarAnunciante(@Valid Anunciante anunciante,
                                   @RequestParam(value = "nextPage", required = false) String nextPage,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("erros", Utils.criarListaDeErrosDaValidacao(bindingResult.getAllErrors()));
            model.addAttribute("anunciante", anunciante);
            return "anunciante/cadastrar";
        }

        if (anunciante.getId() == null) {
            Optional<Usuario> usuario = usuarioRepository.findByEmail(anunciante.getEmail());
            if (usuario.isPresent()) {
                model.addAttribute("erro", "Usuário já cadastrado no sistema");
                return cadastrarAnunciante(model);
            }
        }

        anunciante.setSenha(Utils.encrypt(anunciante.getSenha()));
        anuncianteRepository.save(anunciante);

        model.addAttribute("onSave", "Informações salvas!");
        model.addAttribute("anunciante", anunciante);
        if (nextPage != null && !nextPage.trim().isEmpty()) {
            return nextPage;
        }
        return "login";
    }
}
