package it.epicode.be.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;


@Entity
@Data
public class Postazione {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descrizione;
	
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	
	private int maxOccupanti;
		
	@ManyToOne
	private Edificio edificio;
	
//	@OneToMany(mappedBy="postazione")//--->nome della variabile
//	private List<Prenotazione> p;
}
