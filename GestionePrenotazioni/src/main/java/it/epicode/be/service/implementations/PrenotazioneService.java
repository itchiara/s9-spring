package it.epicode.be.service.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.controller.api.dto.ElencoRegole;
import it.epicode.be.eccezioni.BusinessLogicException;
import it.epicode.be.eccezioni.EntityNotFoundException;
import it.epicode.be.model.Postazione;
import it.epicode.be.model.Prenotazione;
import it.epicode.be.model.Tipo;
import it.epicode.be.model.Utente;
import it.epicode.be.persistence.PostazioneRepository;
import it.epicode.be.persistence.PrenotazioneRepository;
import it.epicode.be.persistence.UtenteRepository;
import it.epicode.be.service.abstractions.AbstractReservationService;

@Service
public class PrenotazioneService implements AbstractReservationService{

	private PrenotazioneRepository prenotRepo;
	private UtenteRepository utenteRepo;
	private PostazioneRepository postRepo;

	@Value("${exception.lessthantwodays}")
	private String lessThanTwoDays;

	@Value("${exception.entitynotfound}")
	private String entityNotFound;

	@Value("${exception.userhasreservation}")
	private String userHasReservation;

	@Value("${exception.workspacealreadyreserved}")
	private String workSpaceAlreadyReserved;

	@Value("${rules.ita}")
	private String rulesIta;

	@Value("${rules.eng}")
	private String rulesEng;

	@Autowired
	public PrenotazioneService(PrenotazioneRepository prenotRepo, UtenteRepository utenteRepo,
			PostazioneRepository postRepo) {

		this.prenotRepo = prenotRepo;
		this.utenteRepo = utenteRepo;
		this.postRepo = postRepo;
	}

	@Override
	public List<Prenotazione> listaPrenotazioni() {
		List<Prenotazione> prenotazioni = prenotRepo.findAll();

		return prenotazioni;
	}

	@Override
	public Optional<Prenotazione> getPrenotazioneById(long id) {
		Optional<Prenotazione> cercata = prenotRepo.findById(id);

		return cercata;
	}

	private boolean isWorkSpaceAvaliable(Postazione p, LocalDate data) {
		Pageable pageable = PageRequest.of(0, 1);
		Page<Prenotazione> paginaPrenotazione = prenotRepo.findByPostazioneAndDataUtilizzo(p, data, pageable);

		return paginaPrenotazione.isEmpty();
	}

	private boolean userHasReservationForDay(Utente u, LocalDate date) {
		Pageable pageable = PageRequest.of(0, 1);
		Page<Prenotazione> paginaPrenotazione = prenotRepo.findByUtenteAndDataUtilizzo(u, date, pageable);

		return paginaPrenotazione.hasContent();
	}

	private boolean diffInDaysLessThan(int numDays, LocalDate firstDate, LocalDate secondDate) {
		LocalDate numDaysBefore = secondDate.minusDays(numDays);
		return firstDate.isAfter(numDaysBefore);
	}

	@Override
	public Prenotazione insertPrenotazione(Prenotazione prenotazione)
			throws BusinessLogicException, EntityNotFoundException {

		if (diffInDaysLessThan(2, prenotazione.getDataPrenotazione(), prenotazione.getDataUtilizzo())) {
			throw new BusinessLogicException(lessThanTwoDays);
		}

		Optional<Utente> u = utenteRepo.findById(prenotazione.getUtente().getId());
		if (u.isEmpty()) {
			throw new EntityNotFoundException(entityNotFound, Utente.class);
		}
		Optional<Postazione> p = postRepo.findById(prenotazione.getPostazione().getId());
		if (p.isEmpty()) {
			throw new EntityNotFoundException(entityNotFound, Postazione.class);
		}

		if (userHasReservationForDay(u.get(), prenotazione.getDataUtilizzo())) {
			throw new BusinessLogicException(userHasReservation);
		}

		if (!isWorkSpaceAvaliable(p.get(), prenotazione.getDataUtilizzo())) {
			throw new BusinessLogicException(workSpaceAlreadyReserved);
		}

		Prenotazione saved = prenotRepo.save(prenotazione);

		return saved;

	}

	@Override
	public ElencoRegole getRulesByLang(String lingua) throws BusinessLogicException {
		ElencoRegole risposta = new ElencoRegole();
		risposta.setLingua(lingua);

		if (lingua.equalsIgnoreCase("ita")) {
			risposta.setRegole(rulesIta);
		} else if (lingua.equalsIgnoreCase("eng")) {
			risposta.setRegole(rulesEng);
		} else {
			throw new BusinessLogicException("Lingua non supportata");
		}

		return risposta;

	}
	
	public Page<Postazione> getPostazioneByTipoAndCitta(Tipo tipo, String citta, Pageable pageable){
		
		return postRepo.findByTipoAndEdificioCitta(tipo, citta,pageable);
	}
}
