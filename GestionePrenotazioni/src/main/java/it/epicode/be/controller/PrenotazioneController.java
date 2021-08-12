package it.epicode.be.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.epicode.be.model.Prenotazione;
import it.epicode.be.persistence.PrenotazioneRepository;

@Controller
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

	@Autowired
	private PrenotazioneRepository pr;
	
	@GetMapping("/all")
	public String getAllReservations(Map<String, Object> model) {
		List<Prenotazione> listaPrenotazioni = pr.findAll();
		model.put("prenotazioni", listaPrenotazioni);
		return "showReservations";
	}
	
	@GetMapping("/prenotazioni/{id}")
	public String listaPrenotazioni(Map<String, Object> model, @PathVariable long id) {
		Optional<Prenotazione> cercata = pr.findById(id);
		if (cercata.isPresent()) {
			model.put("cercata", cercata.get());
			return "cercataPage";
		}
		return "prenotazioneNotFound";

	}
	@GetMapping("/prenotazioni/delete/{id}")
	public String deletePrenotazioneById(@PathVariable long id) {
		Optional<Prenotazione> daEliminare = pr.findById(id);
		if (daEliminare.isPresent()) {
			pr.deleteById(id);
			return "cancellazionePage";	
			}
		return "prenotazioneNotFound";

	}
	
}
