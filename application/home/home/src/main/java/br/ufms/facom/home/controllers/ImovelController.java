package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/imovel")
public class ImovelController {

    @Autowired
    private ImovelRepository imovelRepository;

    @PostMapping(value = "/salvar/{anuncianteId}")
    public Imovel salvar(@PathVariable Long anuncianteId, @RequestBody Imovel imovel) {
        imovel.setAnunciante(new Anunciante());
        imovel.getAnunciante().setId(anuncianteId);
        imovelRepository.save(imovel);
        return imovel;
    }

    @GetMapping(value = "buscar/{anuncianteId}/{imovelId}")
    public List<Imovel> buscar(@PathVariable Long anuncianteId, @PathVariable Long imovelId) {
        if (anuncianteId != null && anuncianteId > 0) {
            return imovelRepository.findByAnuncianteId(anuncianteId);
        } else if (imovelId != null && imovelId > 0) {
            return Arrays.asList(imovelRepository.findById(imovelId).get());
        } else {
            return imovelRepository.findAll();
        }
    }
}
