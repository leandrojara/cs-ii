package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.AdicionalImovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdicionalImovelRepository extends JpaRepository<AdicionalImovel, Long> {
}
