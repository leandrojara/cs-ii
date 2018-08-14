package br.ufms.facom.home.controllers;

import br.ufms.facom.home.repository.AnuncianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anunciante")
public class AnuncianteController {

    @Autowired
    private AnuncianteRepository anuncianteRepository;
}
