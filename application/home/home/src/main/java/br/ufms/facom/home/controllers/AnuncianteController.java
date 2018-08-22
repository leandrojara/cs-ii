package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.bean.ResponseError;
import br.ufms.facom.home.domain.enums.CodeError;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AnuncianteController {

    @Autowired
    private AnuncianteRepository anuncianteRepository;

    @RequestMapping(value = "/anunciante/cadastrar", method = RequestMethod.GET)
    public String cadastrarAnunciante(Model model) {
        model.addAttribute("anunciante", new Anunciante());
        return "anunciante/cadastrar";
    }

    @RequestMapping(value = "/anunciante/salvar", method = RequestMethod.POST)
    public String salvarAnunciante(@Valid Anunciante anunciante, Model model) {
        anuncianteRepository.save(anunciante);
        model.addAttribute("anunciante", anunciante);
        return "login";
    }

    @RequestMapping(value = "/vue/anunciante/salvar", method = RequestMethod.POST)
    public ResponseEntity salvarAnuncianteVue(@Valid @RequestBody Anunciante anunciante, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.criarListaDeErrosDaValidacao(bindingResult.getAllErrors()));
        }

        Optional<Anunciante> anuncianteBanco = anuncianteRepository.findByEmail(anunciante.getEmail());
        if (anuncianteBanco.isPresent()) {
            return new ResponseEntity(new ResponseError(CodeError.USUARIO_EXISTENTE, "E-mail já cadastrado"), HttpStatus.BAD_REQUEST);
        }

        anunciante.setSenha(Utils.encrypt(anunciante.getSenha()));
        anuncianteRepository.save(anunciante);
        return ResponseEntity.ok(anunciante);
    }
}
