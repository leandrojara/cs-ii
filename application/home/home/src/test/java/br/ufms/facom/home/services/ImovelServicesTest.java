package br.ufms.facom.home.services;

import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ImovelServicesTest {

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
        Assertions.assertThat(imovel.getAdicionais().contains(adicionalImovelRepository.getOne(1L))).isEqualTo(true);
        Assertions.assertThat(imovel.getAdicionais().contains(adicionalImovelRepository.getOne(4L))).isEqualTo(false);
    }
}
