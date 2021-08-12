package it.epicode.be.persistence;


import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.epicode.be.model.Postazione;
import it.epicode.be.model.Tipo;

public interface PostazioneRepository extends JpaRepository<Postazione, Long>{

//	@Query("SELECT p.id FROM Postazione p WHERE p.isLibera=true")
//	public List<Postazione> findById();
	
public Page<Postazione> findByTipoAndEdificioCitta(Tipo tipo, String citta, Pageable p);
	
	@Query("SELECT post FROM Postazione post WHERE post.edificio.citta = :citta AND post.tipo = :tipo "
			+ "AND post.id NOT IN (SELECT pre.postazione.id FROM Prenotazione pre WHERE pre.dataUtilizzo = :dataUtilizzo)")
	public Page<Postazione> findAvailableByTypeAndCity(String citta, Tipo tipo,LocalDate dataUtilizzo ,Pageable p);
}
