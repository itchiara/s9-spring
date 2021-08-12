package it.epicode.be.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import it.epicode.be.model.Utente;
import it.epicode.be.persistence.UtenteRepository;

@Controller
public class UtenteController {

	@Autowired
	private UtenteRepository utenteRepo;
	
	@GetMapping("/utenti")
	public String listaUtenti(Map<String, Object> model) {
		List<Utente> listaUtenti = utenteRepo.findAll();
		if(!listaUtenti.isEmpty()) {
			model.put("listaUtenti", listaUtenti);
			return "utentiPage";
		}
		
		return null;
	
	}
}
