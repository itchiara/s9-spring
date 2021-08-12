package it.epicode.be.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;


@Entity
@Data
public class Prenotazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User utente;
	
	private LocalDate dataPrenotazione;
	private LocalDate dataUtilizzo;
	
	@ManyToOne //(fetch = FetchType.EAGER) // le relazioni verso One sono di default Eager(vengono lette da database in ogni caso)
										//,viceversa il default è Lazy(vengono lette solo se richiesto)
	//@JoinColumn(name="postazione_id")  //nome di default della colonna foreign key
	private Postazione postazione;
	
	
}
