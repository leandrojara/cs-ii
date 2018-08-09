package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Cliente;
import br.ufms.facom.home.domain.Response;
import br.ufms.facom.home.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/")
    public Response<?> getAll() {
        return new Response<>(clienteRepository.findAll());
    }

    @PostMapping("/save")
    public Response<?> save(@RequestBody Cliente cliente) {
        clienteRepository.save(cliente);
        return new Response<>(cliente);
    }
}
