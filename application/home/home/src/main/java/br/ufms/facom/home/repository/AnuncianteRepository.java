package br.ufms.facom.home.repository;

import br.ufms.facom.home.domain.Anunciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnuncianteRepository extends JpaRepository<Anunciante, Long> {

    Optional<Anunciante> findByEmail(String email);
}
