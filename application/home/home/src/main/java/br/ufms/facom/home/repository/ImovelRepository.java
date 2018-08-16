package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImovelRepository extends JpaRepository<Imovel, Long> {
}
