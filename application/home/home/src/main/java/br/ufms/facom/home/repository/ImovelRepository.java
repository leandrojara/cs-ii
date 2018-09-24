package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.enums.TipoImovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    public List<Imovel> findByAnuncianteId(Long idAnunciante);

    public Page<Imovel> findByRuaOrBairroOrCidadeOrTipoImovelAllIgnoreCaseOrderByCidadeAscBairroAscRuaAsc(@Nullable String rua,
                                                                                                          @Nullable String bairro,
                                                                                                          @Nullable String cidade,
                                                                                                          @Nullable TipoImovel tipoImovel,
                                                                                                          Pageable pageable);
}
