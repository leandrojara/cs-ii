package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.ImovelImagem;
import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.domain.enums.TipoConservacao;
import br.ufms.facom.home.domain.enums.TipoImovel;
import br.ufms.facom.home.domain.enums.TipoNegocio;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.repository.ImovelRepository;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ImovelController {

    @Autowired
    private ImovelRepository imovelRepository;
    @Autowired
    private AdicionalImovelRepository adicionalImovelRepository;
    @Autowired
    private AnuncianteRepository anuncianteRepository;

    private static final String fileSeparator = System.getProperty("file.separator");
    private static final String uploadingdir = System.getProperty("user.dir") + fileSeparator + "uploadingdir" + fileSeparator;

    @RequestMapping(value = "/imovel/anunciar", method = RequestMethod.GET)
    public String anunciarImovel(Model model) {
        Imovel imovel = new Imovel();
        imovel.setAdicionais(adicionalImovelRepository.findAll());
        model.addAttribute("imovel", imovel);
        model.addAttribute("tiposImovel", TipoImovel.values());
        model.addAttribute("tiposNegocio", TipoNegocio.values());
        model.addAttribute("tiposConservacao", TipoConservacao.values());
        model.addAttribute("usuarioLogado", Utils.getUsuarioLogado());
        return "imovel/anunciar";
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

        imovel.setAdicionais(new ArrayList<>());
        if (adicionais != null) {
            for (long adicional : adicionais) {
                imovel.getAdicionais().add(adicionalImovelRepository.findById(adicional).get());
            }
        }

        imovel.setDataCadastro(new Date());
        Anunciante anunciante = anuncianteRepository.findById(Long.parseLong(usuario.getAuthorities().iterator().next().getAuthority())).get();
        imovel.setAnunciante(anunciante);
        imovelRepository.save(imovel);

        if (uploadingFiles != null) {
            imovel.setImagens(new ArrayList<>());

            for (MultipartFile uploadedFile : uploadingFiles) {
                if (!uploadedFile.isEmpty()) {
                    File file = new File(uploadingdir);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    file = new File(uploadingdir + imovel.getId());
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    file = new File(uploadingdir + imovel.getId() + fileSeparator + uploadedFile.getOriginalFilename());
                    uploadedFile.transferTo(file);

                    ImovelImagem imovelImagem = new ImovelImagem();
                    imovelImagem.setImovel(imovel);
                    imovelImagem.setDiretorio(file.getPath());
                    imovel.getImagens().add(imovelImagem);
                }
            }

            imovelRepository.save(imovel);
        }

        model.addAttribute("onSave", "Imóvel salvo com sucesso!");
        return anunciarImovel(model);
    }
}
