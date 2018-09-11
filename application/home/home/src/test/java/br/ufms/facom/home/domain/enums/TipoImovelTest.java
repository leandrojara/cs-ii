package br.ufms.facom.home.domain.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TipoImovelTest {

    @Test
    public void enumTest() {
        assertEquals(0, TipoImovel.CASA.ordinal());
        assertEquals(1, TipoImovel.APARTAMENTO.ordinal());
        assertEquals(2, TipoImovel.TERRENO.ordinal());
        assertEquals(3, TipoImovel.SOBRADO.ordinal());
        assertEquals(4, TipoImovel.COMERCIAL.ordinal());
        assertEquals(5, TipoImovel.RURAL.ordinal());
    }
}
