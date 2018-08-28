package br.ufms.facom.home.services;

import br.ufms.facom.home.domain.AdicionalImovel;

import java.util.List;

public interface AdicionalImovelServices {

    void setSelecionado(List<AdicionalImovel> adicionais, boolean selecionado);

    void unificaLista(List<AdicionalImovel> adicionais1, List<AdicionalImovel> adicionais2);
}
