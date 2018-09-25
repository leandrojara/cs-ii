package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.*;
import br.ufms.facom.home.domain.enums.TipoConservacao;
import br.ufms.facom.home.domain.enums.TipoImovel;
import br.ufms.facom.home.domain.enums.TipoNegocio;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.repository.ImagemImovelRepository;
import br.ufms.facom.home.repository.ImovelRepository;
import br.ufms.facom.home.services.AdicionalImovelServices;
import br.ufms.facom.home.services.ImovelServices;
import br.ufms.facom.home.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @Autowired
    private ImagemImovelRepository imagemImovelRepository;

    private static int currentPage = 1;
    private static int pageSize = 5;

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
        model.addAttribute("title", "Anunciar Imóvel");
        addFormAttributes(model);
        return "imovel/anunciar";
    }

    @RequestMapping(value = "/imovel/visualizar/{idImovel}", method = RequestMethod.GET)
    public String visualizarImovel(@PathVariable("idImovel") Long idImovel,
                                   Model model) throws IOException {
        Optional<Imovel> imovel = imovelRepository.findById(idImovel);
        if (imovel.isPresent()) {
            imovelServices.findUploadedFiles(imovel.get());
            model.addAttribute("imovel", imovel.get());
            model.addAttribute("usuarioLogado", Utils.getUsuarioLogado());
            return "imovel/visualizar";
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/imovel/editar/{idImovel}", method = RequestMethod.GET)
    public String editarImovel(@PathVariable("idImovel") Long idImovel,
                               Model model) throws IOException {
        Optional<Imovel> imovel = imovelRepository.findById(idImovel);
        if (imovel.isPresent()) {
            adicionalImovelServices.setSelecionado(imovel.get().getAdicionais(), true);
            List<AdicionalImovel> adicionais = adicionalImovelRepository.findAll();
            adicionalImovelServices.unificaLista(imovel.get().getAdicionais(), adicionais);
            imovelServices.findUploadedFiles(imovel.get());
            model.addAttribute("imovel", imovel.get());
            model.addAttribute("isEdit", true);
            model.addAttribute("title", "Editar Imóvel");
            addFormAttributes(model);
            return "imovel/anunciar";
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/imovel/buscar/", method = RequestMethod.GET)
    public String buscarDoAnunciante(Model model,
                                     @RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "size", required = false) Integer size,
                                     @RequestParam(value = "rua", required = false) String rua,
                                     @RequestParam(value = "bairro", required = false) String bairro,
                                     @RequestParam(value = "cidade", required = false) String cidade) throws IOException {
        if (page != null) {
            currentPage = page;
        }
        if (size != null) {
            pageSize = size;
        }

        Page<Imovel> imoveis = imovelRepository
                .findByRuaOrBairroOrCidadeOrTipoImovelOrAnuncianteIdAllIgnoreCaseOrderByCidadeAscBairroAscRuaAsc
                        (rua, bairro, cidade, null, Utils.getUsuarioLogado().getId(), PageRequest.of(currentPage - 1, pageSize));
        imovelServices.findUploadedFiles(imoveis);
        model.addAttribute("imoveisPage", imoveis);
        model.addAttribute("imoveis", imoveis.getContent());

        int totalPages = imoveis.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "anunciante/meusAnuncios";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("dataCadastro");

        dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                try {
                    setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                } catch (ParseException e) {
                    setValue(null);
                }
            }
        });

    }

    @RequestMapping(value = "/imovel/salvar", method = RequestMethod.POST)
    public String salvarImovel(@Valid Imovel imovel,
                               @RequestParam(value = "adicionais", required = false) long[] adicionais,
                               @RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,
                               @RequestParam("dataCadastro") Date dataCadastro,
                               BindingResult bindingResult, Model model) throws IOException {
        Usuario usuario = Utils.getUsuarioLogado();

        if (bindingResult.hasErrors()) {
            model.addAttribute("erros", Utils.criarListaDeErrosDaValidacao(bindingResult.getAllErrors()));
            imovel.setAdicionais(adicionalImovelRepository.findAll());
            model.addAttribute("imovel", imovel);
            addFormAttributes(model);
            return "imovel/anunciar";
        }

        imovel.setDataCadastro(dataCadastro != null ? dataCadastro : new Date());
        imovelServices.addAdicionais(imovel, adicionais);
        Anunciante anunciante = anuncianteRepository.findById(Long.parseLong(usuario.getAuthorities().iterator().next().getAuthority())).get();
        imovel.setAnunciante(anunciante);
        imovelServices.createImagemEntities(imovel, uploadingFiles);
        imovelRepository.save(imovel);
        imovelServices.saveUploadedFiles(imovel);

        model.addAttribute("onSave", "As informações foram salvas!");
        return anunciarImovel(model);
    }

    @RequestMapping(value = "/imovel/imagem/excluir/{idImagem}", method = RequestMethod.DELETE)
    public ResponseEntity removerImagem(@PathVariable("idImagem") Long idImagem) {
        Optional<ImovelImagem> imovelImagem = imagemImovelRepository.findById(idImagem);
        if (imovelImagem.isPresent()) {
            imagemImovelRepository.deleteById(idImagem);
            imovelServices.removeImagem(imovelImagem.get());
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/imovel/excluir/{idImovel}", method = RequestMethod.DELETE)
    public ResponseEntity removerImovel(@PathVariable("idImovel") Long idImovel) {
        Optional<Imovel> imovel = imovelRepository.findById(idImovel);
        if (imovel.isPresent()) {
            imovelRepository.delete(imovel.get());
            for (ImovelImagem imovelImagem : imovel.get().getImagens()) {
                imovelServices.removeImagem(imovelImagem);
            }

            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
