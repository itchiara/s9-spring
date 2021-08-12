package it.epicode.be.controller.api.dto;

import it.epicode.be.model.Edificio;
import it.epicode.be.model.Postazione;
import it.epicode.be.model.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostazioneDto {

	private long id;
	private String descrizione;
	private Tipo tipo;
	private int numMaxOccupanti;
	private long edificioId;
	private String edificioNome;
	private String edificioCitta;

	public static PostazioneDto fromPostazione(Postazione p) {
		return new PostazioneDto(p.getId(), p.getDescrizione(), p.getTipo(), p.getMaxOccupanti(),
				p.getEdificio().getId(), p.getEdificio().getNome(), p.getEdificio().getCitta());
	}
	
	public Postazione toPostazione() {
		Postazione p = new Postazione();
		p.setId(id);
		p.setDescrizione(descrizione);
		p.setTipo(tipo);
		p.setMaxOccupanti(numMaxOccupanti);
		
		Edificio ed = new Edificio();
		ed.setId(edificioId);
		ed.setNome(edificioNome);
		ed.setCitta(edificioCitta);
		
		p.setEdificio(ed);
		
		return p;
	}

}
