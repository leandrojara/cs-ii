package br.ufms.facom.home.services;

import br.ufms.facom.home.domain.AdicionalImovel;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AdicionalImovelServicesTest {

    @Autowired
    private AdicionalImovelRepository adicionalImovelRepository;
    @Autowired
    private AdicionalImovelServices adicionalImovelServices;

    private List<AdicionalImovel> adicionais1;
    private List<AdicionalImovel> adicionais2;

    @Before
    public void before() {
        adicionais1 = new ArrayList<>();
        adicionais1 = adicionalImovelRepository.findAll();
        adicionais2 = adicionalImovelRepository.findAll();

        adicionais2.remove(0);
        adicionais2.remove(0);
    }

    @Test
    public void setSelecionadoTest() {
        adicionalImovelServices.setSelecionado(adicionais1, true);
        for (AdicionalImovel adicional : adicionais1) {
            Assertions.assertThat(adicional.getSelecionado()).isEqualTo(true);
        }
        for (AdicionalImovel adicional : adicionais2) {
            Assertions.assertThat(adicional.getSelecionado()).isEqualTo(false);
        }
    }

    @Test
    public void unificaListaTest() {
        adicionalImovelServices.unificaLista(adicionais2, adicionais1);
        Assertions.assertThat(adicionais1).hasSize(adicionais2.size());
    }
}
