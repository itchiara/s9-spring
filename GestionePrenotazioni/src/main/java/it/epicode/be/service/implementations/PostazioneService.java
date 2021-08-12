package it.epicode.be.service.implementations;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.model.Postazione;
import it.epicode.be.model.Tipo;
import it.epicode.be.persistence.PostazioneRepository;
import it.epicode.be.service.abstractions.AbstractPostazioneService;

@Service
public class PostazioneService implements AbstractPostazioneService{

	@Autowired
	private PostazioneRepository postazioneRepo;

	@Override
	public Page<Postazione> findByTypeAndCity(Tipo tipo, String citta, Pageable pageable) {
		
		return postazioneRepo.findByTipoAndEdificioCitta(tipo, citta, pageable);
	}

	@Override
	public Page<Postazione> findAvailableByTypeAndCity(Tipo tipo, String citta, LocalDate dataUtilizzo,
			Pageable pageable) {
		return postazioneRepo.findAvailableByTypeAndCity(citta, tipo, dataUtilizzo, pageable);
	}
	
}
