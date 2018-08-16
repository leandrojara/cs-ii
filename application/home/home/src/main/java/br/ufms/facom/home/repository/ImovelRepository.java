package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    public List<Imovel> findByAnuncianteId(Long idAnunciante);
}
