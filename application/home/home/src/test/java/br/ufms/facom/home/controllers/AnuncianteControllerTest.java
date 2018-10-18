package br.ufms.facom.home.controllers;

import br.ufms.facom.home.HomeApplicationTests;
import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.repository.ImovelRepository;
import br.ufms.facom.home.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import br.ufms.facom.home.domain.enums.TipoNegocio;
import br.ufms.facom.home.utils.ReportParameter;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class AnuncianteControllerTest extends HomeApplicationTests {

    @Autowired
    private AnuncianteController AnuncianteController;

    @Autowired
    private AnuncianteRepository anuncianteRepository;
    @Autowired
    private ImovelRepository imovelRepository;

    private Anunciante anunciante;
    private List<Imovel> result;
    private MockMvc mockMvc;

    private String pdf = null;
    private File file;

    @Before
    public void setUp() {

        anunciante = new Anunciante();
        anunciante.setNome("Usuario de teste");
        anunciante.setTelefone("(67)9999-9999");
        anunciante.setEmail("email@teste.com.br");
        anunciante.setSenha(Utils.encrypt("123"));

        Imovel imovel1 = new Imovel();
        imovel1.setDataCadastro(new Date());
        imovel1.setAnunciante(anunciante);
        imovel1.setDescricao("imovel 1");
        imovel1.setRua("av afonso pena");
        imovel1.setTipoNegocio(TipoNegocio.ALUGUEL);
        imovel1.setBairro("bairro centro");
        imovel1.setCidade("campo grande");
        imovel1.setEstado("MS");

        Imovel imovel2 = new Imovel();
        imovel2.setDataCadastro(new Date());
        imovel2.setAnunciante(anunciante);
        imovel2.setTipoNegocio(TipoNegocio.VENDA);
        imovel2.setDescricao("imovel 2");
        imovel2.setRua("rua das garças");
        imovel2.setBairro("bairro vila");
        imovel2.setCidade("campinas");
        imovel2.setEstado("SP");

        anunciante.setImoveis(new ArrayList<>());
        anunciante.getImoveis().add(imovel1);
        anunciante.getImoveis().add(imovel2);

        anuncianteRepository.save(anunciante);

        TipoNegocio tipoNegocio = TipoNegocio.VENDA;
        result = imovelRepository.listagem(anunciante.getId(), tipoNegocio);

        this.mockMvc = MockMvcBuilders.standaloneSetup(AnuncianteController).build();
    }

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

    @Test
    public void gerarRelatorioVendaTest() {
        pdf = Utils.gerarRelatorio("pdf","listagemImoveis.jrxml", result,
                new ReportParameter("titulo", "Listagem de Imóveis para Venda")
        );
        file = new File(pdf);
        Assert.assertTrue(file.exists());
    }

    @After
    public void tearDown() {
        file.delete();
    }

}
