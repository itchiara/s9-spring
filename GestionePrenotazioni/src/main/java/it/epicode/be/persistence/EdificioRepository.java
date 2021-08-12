package it.epicode.be.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.model.Edificio;

public interface EdificioRepository extends JpaRepository<Edificio, Long>{

	public List<Edificio> findByNome (String nome);
	public List<Edificio> findByNomeAndIndirizzo (String nome, String indirizzo);
}
