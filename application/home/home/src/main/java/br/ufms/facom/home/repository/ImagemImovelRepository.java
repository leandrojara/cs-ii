package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.ImovelImagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemImovelRepository extends JpaRepository<ImovelImagem, Long> {
}
