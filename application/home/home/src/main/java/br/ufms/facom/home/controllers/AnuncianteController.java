package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.domain.enums.TipoFormato;
import br.ufms.facom.home.domain.enums.TipoNegocio;
import br.ufms.facom.home.domain.enums.TipoRelatorio;
import br.ufms.facom.home.domain.enums.TipoTemplate;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.repository.ImovelRepository;
import br.ufms.facom.home.repository.UsuarioRepository;
import br.ufms.facom.home.utils.ReportParameter;
import br.ufms.facom.home.utils.ReportUtils;
import br.ufms.facom.home.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Controller
public class AnuncianteController {

    @Autowired
    private AnuncianteRepository anuncianteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ImovelRepository imovelRepository;

    private static final String ANUNCIANTE_ATTR = "anunciante";

    @GetMapping("/anunciante/cadastrar")
    public String cadastrarAnunciante(Model model) {
        model.addAttribute(ANUNCIANTE_ATTR, new Anunciante());
        return "anunciante/cadastrar";
    }

    @GetMapping("/anunciante/editar/")
    public String editarAnunciante(Model model) {
        Optional<Anunciante> anunciante = anuncianteRepository.findById(Utils.getUsuarioLogado().getId());
        anunciante.ifPresent(anunciante1 -> model.addAttribute(ANUNCIANTE_ATTR, anunciante1));
        model.addAttribute("usuarioLogado", Utils.getUsuarioLogado());
        return "anunciante/editar";
    }

    @GetMapping("/anunciante/excluir/")
    public String excluir() {
        anuncianteRepository.deleteById(Utils.getUsuarioLogado().getId());
        return "login";
    }

    @PostMapping("/anunciante/salvar")
    public String salvarAnunciante(@Valid Anunciante anunciante,
                                   @RequestParam(value = "nextPage", required = false) String nextPage,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("erros", Utils.criarListaDeErrosDaValidacao(bindingResult.getAllErrors()));
            model.addAttribute(ANUNCIANTE_ATTR, anunciante);
            return "anunciante/cadastrar";
        }

        if (anunciante.getId() == null) {
            Optional<Usuario> usuario = usuarioRepository.findByEmail(anunciante.getEmail());
            if (usuario.isPresent()) {
                model.addAttribute("erro", "Usuário já cadastrado no sistema");
                return cadastrarAnunciante(model);
            }
        } else {
            anunciante.setImoveis(imovelRepository.findByAnuncianteId(anunciante.getId()));
        }

        anunciante.setSenha(Utils.encrypt(anunciante.getSenha()));
        anuncianteRepository.save(anunciante);

        model.addAttribute("onSave", "Informações salvas!");
        model.addAttribute(ANUNCIANTE_ATTR, anunciante);
        if (nextPage != null && !nextPage.trim().isEmpty()) {
            return nextPage;
        }
        return "login";
    }

    @GetMapping("/anunciante/gerarRelatorio")
    @ResponseBody
    public ResponseEntity<Resource> listagem(@RequestParam("tipoRelatorio") TipoRelatorio tipoRelatorio,
                                             @RequestParam("tipoFormato") TipoFormato tipoFormato,
                                             @RequestParam("tipoTemplate") TipoTemplate tipoTemplate) {
        List<Imovel> result = null;
        switch (tipoRelatorio) {
            case LISTAGEM_VENDAS:
                result = imovelRepository.listagem(Utils.getUsuarioLogado().getId(), TipoNegocio.VENDA);
                break;
            case LISTAGEM_ALUGUEL:
                result = imovelRepository.listagem(Utils.getUsuarioLogado().getId(), TipoNegocio.ALUGUEL);
                break;
            default:
                throw new RuntimeException("O tipo de relatório informado não possui implementação!");
        }

        for (Imovel imovel : result) {
            imovel.setAdicionais(null);
            imovel.setImagens(null);
            imovel.setAnunciante(null);
        }

        try {
            String relatorioGerado = ReportUtils.gerarRelatorio(tipoFormato, tipoTemplate, tipoRelatorio.getJrxml(), result,
                    new ReportParameter("titulo", tipoRelatorio.getDescricao() + "\nAnunciante: " + Utils.getUsuarioLogado().getNome())
            );

            if (relatorioGerado != null) {
                Resource file = new UrlResource(
                        new File(relatorioGerado).toURI()
                );

                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, tipoFormato.getMediaType())
                        .body(file);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
}
