package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.enums.TipoConservacao;
import br.ufms.facom.home.domain.enums.TipoImovel;
import br.ufms.facom.home.domain.enums.TipoNegocio;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import br.ufms.facom.home.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class ImovelController {

    @Autowired
    private ImovelRepository imovelRepository;
    @Autowired
    private AdicionalImovelRepository adicionalImovelRepository;

    @RequestMapping(value = "/imovel/anunciar", method = RequestMethod.GET)
    public String anunciar(Model model) {
        model.addAttribute("imovel", new Imovel());
        model.addAttribute("tipoImovel", TipoImovel.values());
        model.addAttribute("tipoNegocio", TipoNegocio.values());
        model.addAttribute("tipoConservacao", TipoConservacao.values());
        model.addAttribute("adicionais", adicionalImovelRepository.findAll());
        return "imovel/anunciar";
    }

    @RequestMapping(value = "/imovel/salvar", method = RequestMethod.POST)
    public String salvar(@Valid Imovel imovel, Model model) {
        imovel.setDataCadastro(new Date());
        imovel.setAnunciante((Anunciante) model.asMap().get("anunciante"));
        imovelRepository.save(imovel);
        return anunciar(model);
    }
}
