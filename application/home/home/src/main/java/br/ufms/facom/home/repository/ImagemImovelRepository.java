package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.ImovelImagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemImovelRepository extends JpaRepository<ImovelImagem, Long> {

    List<ImovelImagem> findByImovelId(Long imovelId);
}
