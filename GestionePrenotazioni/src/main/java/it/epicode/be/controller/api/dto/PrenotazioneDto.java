package it.epicode.be.controller.api.dto;

import java.time.LocalDate;

import it.epicode.be.model.Edificio;
import it.epicode.be.model.Postazione;
import it.epicode.be.model.Prenotazione;
import it.epicode.be.model.Tipo;
import it.epicode.be.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//data transfer object=dto
public class PrenotazioneDto { 
	
	private Long idUtente;
	private String usernameUtente;
	
	private Long idPostazione;
	private Tipo tipoPostazione;
	
	private Long id;
	private LocalDate dataPrenotazione;
	
	private Long idEdificio;
	private String nomeEdificio;
	private String cittaEdificio;

	public static PrenotazioneDto fromPrenotazione(Prenotazione p) {
		return new PrenotazioneDto(p.getUtente().getId(),p.getUtente().getUsername(),p.getPostazione().getId(),p.getPostazione().getTipo(),
				p.getId(),p.getDataPrenotazione(),
				p.getPostazione().getEdificio().getId(),p.getPostazione().getEdificio().getNome(),
				p.getPostazione().getEdificio().getCitta());
	}
	
	public Prenotazione toPrenotazione() {
		Prenotazione p = new Prenotazione();
		p.setId(id);
		p.setDataPrenotazione(dataPrenotazione);
		
		User u = new User();
		u.setId(idUtente);
		u.setUsername(usernameUtente);
		p.setUtente(u);
		
		Edificio e = new Edificio();
		e.setId(idEdificio);
		e.setCitta(cittaEdificio);
		e.setNome(nomeEdificio);
		
		Postazione post = new Postazione();
		post.setId(idPostazione);
		post.setTipo(tipoPostazione);
		post.setEdificio(e);
		
		p.setPostazione(post);
		
		return p;
	}

	
}
