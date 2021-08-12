package it.epicode.be.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import it.epicode.be.model.User;
import it.epicode.be.persistence.UserRepository;

@Controller
public class UtenteController {

	@Autowired
	private UserRepository utenteRepo;
	
	@GetMapping("/utenti")
	public String listaUtenti(Map<String, Object> model) {
		Iterable<User> listaUtenti = utenteRepo.findAll();
		if(!((CharSequence) listaUtenti).isEmpty()) {
			model.put("listaUtenti", listaUtenti);
			return "utentiPage";
		}
		
		return null;
	
	}
}
