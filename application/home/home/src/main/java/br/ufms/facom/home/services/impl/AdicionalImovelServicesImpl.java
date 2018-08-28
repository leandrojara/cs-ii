package br.ufms.facom.home.services.impl;

import br.ufms.facom.home.domain.AdicionalImovel;
import br.ufms.facom.home.services.AdicionalImovelServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdicionalImovelServicesImpl implements AdicionalImovelServices {

    @Override
    public void setSelecionado(List<AdicionalImovel> adicionais, boolean selecionado) {
        if (adicionais != null) {
            for (AdicionalImovel adicional : adicionais) {
                adicional.setSelecionado(selecionado);
            }
        }
    }

    @Override
    public void unificaLista(List<AdicionalImovel> adicionais1, List<AdicionalImovel> adicionais2) {
        for (AdicionalImovel adicionalImovel2 : adicionais2) {
            boolean naLista = false;
            for (AdicionalImovel adicionalImovel1 : adicionais1) {
                if (adicionalImovel1.getId().equals(adicionalImovel2.getId())) {
                    naLista = true;
                }
            }

            if (!naLista) {
                adicionais1.add(adicionalImovel2);
            }
        }
    }
}
