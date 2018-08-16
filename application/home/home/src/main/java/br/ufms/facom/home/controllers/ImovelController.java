package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
