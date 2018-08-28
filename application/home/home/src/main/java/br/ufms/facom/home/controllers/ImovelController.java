package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.AdicionalImovel;
import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.domain.enums.TipoConservacao;
import br.ufms.facom.home.domain.enums.TipoImovel;
import br.ufms.facom.home.domain.enums.TipoNegocio;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.repository.ImovelRepository;
import br.ufms.facom.home.services.AdicionalImovelServices;
import br.ufms.facom.home.services.ImovelServices;
import br.ufms.facom.home.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ImovelController {

    @Autowired
    private ImovelRepository imovelRepository;
    @Autowired
    private AdicionalImovelRepository adicionalImovelRepository;
    @Autowired
    private AnuncianteRepository anuncianteRepository;
    @Autowired
    private ImovelServices imovelServices;
    @Autowired
    private AdicionalImovelServices adicionalImovelServices;

    private void addFormAttributes(Model model) {
        model.addAttribute("tiposImovel", TipoImovel.values());
        model.addAttribute("tiposNegocio", TipoNegocio.values());
        model.addAttribute("tiposConservacao", TipoConservacao.values());
        model.addAttribute("usuarioLogado", Utils.getUsuarioLogado());
    }

    @RequestMapping(value = "/imovel/anunciar", method = RequestMethod.GET)
    public String anunciarImovel(Model model) {
        Imovel imovel = new Imovel();
        imovel.setAdicionais(adicionalImovelRepository.findAll());
        model.addAttribute("imovel", imovel);
        addFormAttributes(model);
        return "imovel/anunciar";
    }

    @RequestMapping(value = "/imovel/editar", method = RequestMethod.GET)
    public String editarImovel(@RequestParam("idImovel") Long idImovel,
                               Model model) throws IOException {
        Optional<Imovel> imovel = imovelRepository.findById(idImovel);
        if (imovel.isPresent()) {
            adicionalImovelServices.setSelecionado(imovel.get().getAdicionais(), true);
            List<AdicionalImovel> adicionais = adicionalImovelRepository.findAll();
            adicionalImovelServices.unificaLista(imovel.get().getAdicionais(), adicionais);
            imovelServices.findUploadedFiles(imovel.get());
            model.addAttribute("imovel", imovel.get());
            addFormAttributes(model);
            return "imovel/anunciar";
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/imovel/salvar", method = RequestMethod.POST)
    public String salvarImovel(@Valid Imovel imovel,
                               @RequestParam(value = "adicionais", required = false) long[] adicionais,
                               @RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,
                               BindingResult bindingResult, Model model) throws IOException {
        Usuario usuario = Utils.getUsuarioLogado();

        if (bindingResult.hasErrors()) {
            model.addAttribute("erros", Utils.criarListaDeErrosDaValidacao(bindingResult.getAllErrors()));
            imovel.setAdicionais(adicionalImovelRepository.findAll());
            model.addAttribute("imovel", imovel);
            model.addAttribute("tiposImovel", TipoImovel.values());
            model.addAttribute("tiposNegocio", TipoNegocio.values());
            model.addAttribute("tiposConservacao", TipoConservacao.values());
            if (usuario != null) {
                model.addAttribute("usuarioLogado", usuario);
            }
            return "imovel/anunciar";
        }

        imovel.setDataCadastro(new Date());
        imovelServices.addAdicionais(imovel, adicionais);
        Anunciante anunciante = anuncianteRepository.findById(Long.parseLong(usuario.getAuthorities().iterator().next().getAuthority())).get();
        imovel.setAnunciante(anunciante);
        imovelServices.createImagemEntities(imovel, uploadingFiles);
        imovelRepository.save(imovel);
        imovelServices.saveUploadedFiles(imovel);

        model.addAttribute("onSave", "Imóvel salvo com sucesso!");
        return anunciarImovel(model);
    }
}
