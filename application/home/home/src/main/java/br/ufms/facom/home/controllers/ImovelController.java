package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.AdicionalImovel;
import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.enums.TipoConservacao;
import br.ufms.facom.home.domain.enums.TipoImovel;
import br.ufms.facom.home.domain.enums.TipoNegocio;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.repository.ImovelRepository;
import br.ufms.facom.home.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class ImovelController {

    @Autowired
    private ImovelRepository imovelRepository;
    @Autowired
    private AdicionalImovelRepository adicionalImovelRepository;
    @Autowired
    private AnuncianteRepository anuncianteRepository;

    @RequestMapping(value = "/imovel/anunciar", method = RequestMethod.GET)
    public String anunciarImovel(Model model) {
        Imovel imovel = new Imovel();
        imovel.setAdicionais(adicionalImovelRepository.findAll());
        model.addAttribute("imovel", imovel);
        model.addAttribute("tiposImovel", TipoImovel.values());
        model.addAttribute("tiposNegocio", TipoNegocio.values());
        model.addAttribute("tiposConservacao", TipoConservacao.values());
        return "imovel/anunciar";
    }

    @RequestMapping(value = "/imovel/salvar", method = RequestMethod.POST)
    public String salvarImovel(@Valid Imovel imovel,
                               @RequestParam(value = "adicionais", required = false) long[] adicionais,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("erros", Utils.criarListaDeErrosDaValidacao(bindingResult.getAllErrors()));
            imovel.setAdicionais(adicionalImovelRepository.findAll());
            model.addAttribute("imovel", imovel);
            model.addAttribute("tiposImovel", TipoImovel.values());
            model.addAttribute("tiposNegocio", TipoNegocio.values());
            model.addAttribute("tiposConservacao", TipoConservacao.values());
            return "imovel/anunciar";
        }

        imovel.setAdicionais(new ArrayList<>());
        if (adicionais != null) {
            for (long adicional : adicionais) {
                imovel.getAdicionais().add(new AdicionalImovel(adicional));
            }
        }

        imovel.setDataCadastro(new Date());
        imovel.setAnunciante((Anunciante) model.asMap().get("anunciante"));
        imovelRepository.save(imovel);
        return anunciarImovel(model);
    }

    @RequestMapping(value = "/imovel/buscar", method = RequestMethod.GET)
    public String buscarTodosImoveis(Model model) {
        model.addAttribute("imoveis", imovelRepository.findAll());
        return "index";
    }

    @RequestMapping(value = "/vue/imovel/salvar/{idUsuario}", method = RequestMethod.POST)
    public ResponseEntity salvarImovelVue(@Valid @RequestBody Imovel imovel, @PathVariable("idUsuario") Long idUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.criarListaDeErrosDaValidacao(bindingResult.getAllErrors()));
        }

        Optional<Anunciante> anunciante = anuncianteRepository.findById(idUsuario);
        imovel.setAnunciante(anunciante.get());
        imovelRepository.save(imovel);
        return ResponseEntity.ok(imovel);
    }

    @RequestMapping(value = "/vue/imovel/buscar", method = RequestMethod.GET)
    public ResponseEntity buscarTodosImoveisVue() {
        return ResponseEntity.ok(imovelRepository.findAll());
    }
}
