package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.enums.TipoImovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
            " and (?5 is null or anunciante.id = ?5)" +
            " Order by cidade asc, bairro asc, rua asc")
    public Page<Imovel> findByParameters(@Nullable String rua,
                                         @Nullable String bairro,
                                         @Nullable String cidade,
                                         @Nullable TipoImovel tipoImovel,
                                         @Nullable Long anuncianteId,
                                         Pageable pageable);

    @Query("Select upper(i.rua) From Imovel i" +
            " Where (?1 is null or lower(i.rua) like '%'||lower(?1)||'%')" +
            " group by upper(i.rua)")
    public List<String> findRuas(@Nullable String rua,
                                 Pageable pageable);

    default List<String> findRuas(@Nullable String rua) {
        return findRuas(rua, PageRequest.of(0, 6));
    }

    @Query("Select upper(i.bairro) From Imovel i" +
            " Where (?1 is null or lower(i.bairro) like '%'||lower(?1)||'%')" +
            " group by upper(i.bairro)")
    public List<String> findBairros(@Nullable String bairro,
                                    Pageable pageable);

    default List<String> findBairros(@Nullable String bairro) {
        return findBairros(bairro, PageRequest.of(0, 6));
    }

    @Query("Select upper(i.cidade||' - '||i.estado) From Imovel i" +
            " Where (?1 is null or lower(i.cidade) like '%'||lower(?1)||'%')" +
            " group by upper(i.cidade||' - '||i.estado)")
    public List<String> findCidades(@Nullable String cidade,
                                    Pageable pageable);

    default List<String> findCidades(@Nullable String cidade) {
        return findCidades(cidade, PageRequest.of(0, 6));
    }
}
