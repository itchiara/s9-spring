package it.epicode.be.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{

}
