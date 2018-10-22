package br.ufms.facom.home.controllers;

import br.ufms.facom.home.HomeApplicationTests;
import br.ufms.facom.home.domain.Anunciante;
import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.enums.TipoFormato;
import br.ufms.facom.home.domain.enums.TipoImovel;
import br.ufms.facom.home.domain.enums.TipoNegocio;
import br.ufms.facom.home.domain.enums.TipoRelatorio;
import br.ufms.facom.home.repository.AnuncianteRepository;
import br.ufms.facom.home.repository.ImovelRepository;
import br.ufms.facom.home.utils.ReportParameter;
import br.ufms.facom.home.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        imovel1.setTipoImovel(TipoImovel.CASA);
        imovel1.setBairro("bairro centro");
        imovel1.setCidade("campo grande");
        imovel1.setEstado("MS");
        imovel1.setValorImovel(0d);

        Imovel imovel2 = new Imovel();
        imovel2.setDataCadastro(new Date());
        imovel2.setAnunciante(anunciante);
        imovel2.setTipoNegocio(TipoNegocio.VENDA);
        imovel2.setTipoImovel(TipoImovel.CASA);
        imovel2.setDescricao("imovel 2");
        imovel2.setRua("rua das garças");
        imovel2.setBairro("bairro vila");
        imovel2.setCidade("campinas");
        imovel2.setEstado("SP");
        imovel2.setValorImovel(0d);

        anunciante.setImoveis(new ArrayList<>());
        anunciante.getImoveis().add(imovel1);
        anunciante.getImoveis().add(imovel2);

        anuncianteRepository.save(anunciante);

        result = imovelRepository.listagem(anunciante.getId(), TipoNegocio.VENDA);
        for (Imovel imovel : result) {
            imovel.setAdicionais(null);
            imovel.setImagens(null);
            imovel.setAnunciante(null);
        }

        this.mockMvc = MockMvcBuilders.standaloneSetup(AnuncianteController).build();

        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return anunciante;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return anunciante.getNome();
            }
        });
    }

    @Test
    public void contextLoad() throws Exception {
        Assertions.assertThat(AnuncianteController).isNotNull();
    }

    @Test
    public void gerarRelatorioController() throws Exception {
        Utils.jasperdir = Utils.jasperdir.replace("\\home\\jasperdir", "\\jasperdir");
        Utils.reportdir = Utils.reportdir.replace("\\home\\reportdir", "\\reportdir");

        for (TipoFormato tipoFormato : TipoFormato.values()) {
            mockMvc.perform(
                    get("/anunciante/gerarRelatorio")
                            .param("tipoRelatorio", TipoRelatorio.LISTAGEM_VENDAS.toString())
                            .param("tipoFormato", tipoFormato.toString())
            )
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void gerarRelatorioVendaTest() {
        Utils.jasperdir = Utils.jasperdir.replace("\\home\\jasperdir", "\\jasperdir");
        Utils.reportdir = Utils.reportdir.replace("\\home\\reportdir", "\\reportdir");

        for (TipoFormato tipoFormato : TipoFormato.values()) {
            String pdf = Utils.gerarRelatorio(tipoFormato, TipoRelatorio.LISTAGEM_VENDAS.getJrxml(), result,
                    new ReportParameter("titulo", TipoRelatorio.LISTAGEM_VENDAS.getDescricao())
            );
            Assert.assertNotNull(pdf);
            File file = new File(pdf);
            Assert.assertTrue(file.exists());
            Assert.assertTrue(file.delete());
        }
    }
}
