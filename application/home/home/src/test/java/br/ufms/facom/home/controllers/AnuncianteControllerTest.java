package br.ufms.facom.home.controllers;

import br.ufms.facom.home.HomeApplicationTests;
import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class AnuncianteControllerTest extends HomeApplicationTests {

    @Autowired
    private AnuncianteController AnuncianteController;

    private MockMvc mockMvc;

    @Test
    public void contextLoad() throws Exception {
        Assertions.assertThat(AnuncianteController).isNotNull();
    }

    @Test
    public void gerarRelatorioVenda() throws Exception {
        mockMvc.perform(get("/anunciante/listagemVenda"))
                .andExpect(status().isOk());
    }

    @Test
    public void gerarRelatorioAluguel() throws Exception {
        mockMvc.perform(get("/anunciante/listagemAluguel"))
                .andExpect(status().isOk());
    }

}
