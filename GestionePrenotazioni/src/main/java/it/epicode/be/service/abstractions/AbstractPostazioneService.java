package it.epicode.be.service.abstractions;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.epicode.be.eccezioni.EntityNotFoundException;
import it.epicode.be.model.Postazione;
import it.epicode.be.model.Tipo;

public interface AbstractPostazioneService {

	public Page<Postazione> findByTypeAndCity(Tipo tipo, String citta, Pageable pageable);
	public Page<Postazione> findAvailableByTypeAndCity(Tipo tipo, String citta,LocalDate dataUtilizzo, Pageable pageable);
	void deletePostazione(long id) throws EntityNotFoundException;
	Postazione updatePostazioneOld(long id, int numeroMax, long edificioId) throws EntityNotFoundException;
	Postazione updatePostazione(Postazione p) throws EntityNotFoundException;
	
}
