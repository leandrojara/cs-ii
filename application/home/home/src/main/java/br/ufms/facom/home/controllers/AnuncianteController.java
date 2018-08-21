package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.repository.AnuncianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class AnuncianteController {

    @Autowired
    private AnuncianteRepository anuncianteRepository;

    @RequestMapping(name = "/anunciante/cadastrar", method = RequestMethod.GET)
    public String cadastrar(Model model) {
        model.addAttribute("anunciante", new Anunciante());
        return "anunciante/cadastrar";
    }

    @RequestMapping(name = "/anunciante/salvar", method = RequestMethod.POST)
    public String salvar(@Valid Anunciante anunciante, Model model) {
        anuncianteRepository.save(anunciante);
        model.addAttribute("anunciante", anunciante);
        return "login";
    }
}
