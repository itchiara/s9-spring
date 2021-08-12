package it.epicode.be.service.implementations;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.eccezioni.EntityNotFoundException;
import it.epicode.be.model.Edificio;
import it.epicode.be.model.Postazione;
import it.epicode.be.model.Tipo;
import it.epicode.be.persistence.EdificioRepository;
import it.epicode.be.persistence.PostazioneRepository;
import it.epicode.be.service.abstractions.AbstractPostazioneService;

@Service
public class PostazioneService implements AbstractPostazioneService{

	@Value("${exception.entitynotfound}")
	private String entityNotFound;
	
	@Autowired
	private PostazioneRepository postazioneRepo;
	
	@Autowired
	private EdificioRepository edificioRepo;

	@Override
	public Page<Postazione> findByTypeAndCity(Tipo tipo, String citta, Pageable pageable) {
		
		return postazioneRepo.findByTipoAndEdificioCitta(tipo, citta, pageable);
	}

	@Override
	public Page<Postazione> findAvailableByTypeAndCity(Tipo tipo, String citta, LocalDate dataUtilizzo,
			Pageable pageable) {
		return postazioneRepo.findAvailableByTypeAndCity(citta, tipo, dataUtilizzo, pageable);
	}
	
	@Override
	public void deletePostazione(long id) throws EntityNotFoundException {
		Optional<Postazione> pDel = postazioneRepo.findById(id);
		if (pDel.isEmpty()) {
			throw new EntityNotFoundException(entityNotFound, Postazione.class);
		}
		postazioneRepo.deleteById(id);
	}

	@Override
	public Postazione updatePostazioneOld(long id, int numeroMax, long edificioId) throws EntityNotFoundException {
		Optional<Postazione> pUp = postazioneRepo.findById(id);
		Optional<Edificio> ed = edificioRepo.findById(edificioId);
		if (pUp.isEmpty()) {
			throw new EntityNotFoundException(entityNotFound, Postazione.class);
		}
		if (ed.isEmpty()) {
			throw new EntityNotFoundException(entityNotFound, Edificio.class);
		}

		Postazione p = pUp.get();
		p.setMaxOccupanti(numeroMax);
		p.setEdificio(ed.get());
		postazioneRepo.save(p);
		return p;

	}

	@Override
	public Postazione updatePostazione(Postazione p) throws EntityNotFoundException {
		Optional<Postazione> pUpd = postazioneRepo.findById(p.getId());
		Optional<Edificio> ed = edificioRepo.findById(p.getEdificio().getId());
		if (pUpd.isEmpty()) {
			throw new EntityNotFoundException(entityNotFound, Postazione.class);
		}

		if (ed.isEmpty()) {
			throw new EntityNotFoundException(entityNotFound, Edificio.class);
		}

		
		postazioneRepo.save(p);
		return p;

	}
	
}
