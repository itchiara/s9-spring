package it.epicode.be.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Data;



@Entity
@Data
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String nome;
	private String cognome;
	private String nomeCompleto = nome + cognome;
	private String email;
	
//	@OneToMany(mappedBy="utente")
//	private List<Prenotazione> listaPrenotazione;
}
