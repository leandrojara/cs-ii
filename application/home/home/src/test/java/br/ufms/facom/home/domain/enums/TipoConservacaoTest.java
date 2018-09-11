package br.ufms.facom.home.domain.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TipoConservacaoTest {

    @Test
    public void enumTest() {
        assertEquals(0, TipoConservacao.NOVO.ordinal());
        assertEquals(1, TipoConservacao.USADO.ordinal());
        assertEquals(2, TipoConservacao.EM_CONSTRUCAO.ordinal());
        assertEquals(3, TipoConservacao.PLANTA.ordinal());
    }
}
