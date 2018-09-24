package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.enums.TipoImovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    public List<Imovel> findByAnuncianteId(Long idAnunciante);

    @Query(" Select i From Imovel i" +
            " Where (?1 is null or lower(rua) like '%'||lower(?1)||'%')" +
            " and (?2 is null or lower(bairro) like '%'||lower(?2)||'%')" +
            " and (?3 is null or lower(cidade) like '%'||lower(?3)||'%')" +
            " and (?4 is null or tipoImovel = ?4)" +
            " Order by cidade asc, bairro asc, rua asc")
    public Page<Imovel> findByRuaOrBairroOrCidadeOrTipoImovelAllIgnoreCaseOrderByCidadeAscBairroAscRuaAsc(@Nullable String rua,
                                                                                                          @Nullable String bairro,
                                                                                                          @Nullable String cidade,
                                                                                                          @Nullable TipoImovel tipoImovel,
                                                                                                          Pageable pageable);
}
