package br.ufms.facom.home.controllers;

import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.Usuario;
import br.ufms.facom.home.domain.enums.TipoImovel;
import br.ufms.facom.home.repository.ImovelRepository;
import br.ufms.facom.home.repository.UsuarioRepository;
import br.ufms.facom.home.services.ImovelServices;
import br.ufms.facom.home.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UsuarioController implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ImovelRepository imovelRepository;
    @Autowired
    private ImovelServices imovelServices;

    private static int currentPage = 1;
    private static int pageSize = 5;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            List<GrantedAuthority> grantList = new ArrayList<>();
            GrantedAuthority authority = new SimpleGrantedAuthority(usuario.get().getId().toString());
            grantList.add(authority);

            Usuario user = usuario.get();
            user.setGrantList(grantList);
            return user;
        } else {
            throw new UsernameNotFoundException("Usuário não cadastrado no sistema.");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String redirectLogin() {
        return "login";
    }

    @RequestMapping(value = "/{rua}/{bairro}/{cidade}/{tipoImovel}", method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @PathVariable(value = "rua", required = false) String rua,
                        @PathVariable(value = "bairro", required = false) String bairro,
                        @PathVariable(value = "cidade", required = false) String cidade,
                        @PathVariable(value = "tipoImovel", required = false) TipoImovel tipoImovel) throws IOException {
        model.addAttribute("usuarioLogado", Utils.getUsuarioLogado());

        page.ifPresent(p -> currentPage = p);
        size.ifPresent(p -> pageSize = p);

        Page<Imovel> imoveis = imovelRepository
                .findByRuaOrBairroOrCidadeOrTipoImovelAllIgnoreCaseOrderByCidadeAscBairroAscRuaAsc
                        (rua, bairro, cidade, tipoImovel, PageRequest.of(currentPage - 1, pageSize));
        imovelServices.findUploadedFiles(imoveis);
        model.addAttribute("imoveis", imoveis);

        int totalPages = imoveis.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "index";
    }
}
