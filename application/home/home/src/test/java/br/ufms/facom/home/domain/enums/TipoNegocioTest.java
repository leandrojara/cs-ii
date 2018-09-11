package br.ufms.facom.home.domain.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TipoNegocioTest {

    @Test
    public void enumTest() {
        assertEquals(0, TipoNegocio.VENDA.ordinal());
        assertEquals(1, TipoNegocio.ALUGUEL.ordinal());
    }
}
