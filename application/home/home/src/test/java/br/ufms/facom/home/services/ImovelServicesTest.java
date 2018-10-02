package br.ufms.facom.home.services;

import br.ufms.facom.home.HomeApplicationTests;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ImovelServicesTest extends HomeApplicationTests {

    @Autowired
    private ImovelServices imovelServices;
    @Autowired
    private AdicionalImovelRepository adicionalImovelRepository;

    @Test
    public void addAdicionaisTest() {
        Imovel imovel = new Imovel();
        Assertions.assertThat(imovel.getAdicionais()).isNull();

        imovelServices.addAdicionais(imovel, new long[]{1, 2, 3});
        Assertions.assertThat(imovel.getAdicionais()).isNotNull();
        Assertions.assertThat(imovel.getAdicionais()).isNotEmpty();
        Assertions.assertThat(imovel.getAdicionais()).hasSize(3);
        Assertions.assertThat(imovel.getAdicionais().contains(adicionalImovelRepository.findById(1L).get())).isTrue();
        Assertions.assertThat(imovel.getAdicionais().contains(adicionalImovelRepository.findById(4L).get())).isFalse();
    }
}
