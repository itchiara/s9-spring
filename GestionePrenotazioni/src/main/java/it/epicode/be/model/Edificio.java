package it.epicode.be.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Entity
@Data
public class Edificio {

	private String nome;
	private String indirizzo;
	private String citta;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnore inutile se usiamo Dto come output(data transfer object)-->altrimenti va in loop
	@OneToMany(mappedBy="edificio")
	private List<Postazione> listaPostazioni;
}
