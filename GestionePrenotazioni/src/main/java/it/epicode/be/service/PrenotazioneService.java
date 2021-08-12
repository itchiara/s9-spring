package it.epicode.be.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import it.epicode.be.controller.api.dto.PrenotazioneDto;
import it.epicode.be.model.Prenotazione;
import it.epicode.be.persistence.PrenotazioneRepository;
@Service
public class PrenotazioneService {

	private PrenotazioneRepository prenotRepo;
	
	@Autowired
	public PrenotazioneService(PrenotazioneRepository prenotRepo) {
		this.prenotRepo = prenotRepo;
	}
	
	
	public List<PrenotazioneDto> listaPrenotazioni() {
		List<Prenotazione> prenotazioni = prenotRepo.findAll();
		//prenotazioni.stream().map(p -> PrenotazioneDto.fromPrenotazione(p)).collect(Collectors.toList());-->(fa esattamente la stessa cosa della riga sotto)
		List<PrenotazioneDto> prenotazioniDto = prenotazioni.stream()
				.map(PrenotazioneDto::fromPrenotazione).collect(Collectors.toList());
		
		return prenotazioniDto;
	}
	
	
	public ResponseEntity<PrenotazioneDto> getPrenotazioneById(@PathVariable Long id) {
		Optional<Prenotazione> cercata = prenotRepo.findById(id);
		if(cercata.isPresent()) {
			PrenotazioneDto pDto = PrenotazioneDto.fromPrenotazione(cercata.get());
			return new ResponseEntity<PrenotazioneDto>(pDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<PrenotazioneDto>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	public ResponseEntity<PrenotazioneDto> insertPrenotazione(@RequestBody PrenotazioneDto prenotazioneDto){
		Prenotazione nuovaPrenotazione = prenotazioneDto.toPrenotazione();
		Prenotazione p =prenotRepo.save(nuovaPrenotazione);
		PrenotazioneDto pDto = PrenotazioneDto.fromPrenotazione(p);
		return new ResponseEntity<PrenotazioneDto>(pDto,HttpStatus.CREATED);
	}
}
