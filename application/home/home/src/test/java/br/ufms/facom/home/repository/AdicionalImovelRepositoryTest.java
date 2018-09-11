package br.ufms.facom.home.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdicionalImovelRepositoryTest {

    @Autowired
    private AdicionalImovelRepository adicionalImovelRepository;

    @Test
    public void verificaBancoPovoadoAutomaticamente() {
        Assertions.assertThat(adicionalImovelRepository.findAll()).isNotNull();
        Assertions.assertThat(adicionalImovelRepository.findAll()).isNotEmpty();
        Assertions.assertThat(adicionalImovelRepository.findById(1L).get()).isNotNull();
        Assertions.assertThat(adicionalImovelRepository.findById(1L).get().getDescricao()).isEqualTo("CHURRASQUEIRA");
    }
}
