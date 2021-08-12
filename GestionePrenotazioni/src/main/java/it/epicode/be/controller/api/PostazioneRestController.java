package it.epicode.be.controller.api;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.be.controller.api.dto.PostazioneDto;
import it.epicode.be.eccezioni.EntityNotFoundException;
import it.epicode.be.model.Postazione;
import it.epicode.be.model.Tipo;
import it.epicode.be.service.abstractions.AbstractPostazioneService;

@RestController
@RequestMapping("/api/postazioni")
public class PostazioneRestController {

	@Autowired
	private AbstractPostazioneService postazioneService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Page<PostazioneDto>> findPostazioni(@RequestParam Tipo tipo,
			@RequestParam String citta, @RequestParam int pageNum, @RequestParam int pageSize,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Optional<LocalDate> dataUtilizzo) {

		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Postazione> paginaPostazioni = null;
		if (dataUtilizzo.isEmpty()) {
			paginaPostazioni = postazioneService.findByTypeAndCity(tipo, citta, pageable);
			System.out.println("Senza data");
		} else {
			paginaPostazioni = postazioneService.findAvailableByTypeAndCity(tipo, citta, dataUtilizzo.get(), pageable);
			System.out.println("Con data");
		}

		Page<PostazioneDto> paginaDto = paginaPostazioni.map(PostazioneDto::fromPostazione);

		return new ResponseEntity<Page<PostazioneDto>>(paginaDto, HttpStatus.OK);

	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> deletePostazioneById(@PathVariable("id") long id) {
		try {
			postazioneService.deletePostazione(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/{idPost}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updatePostazioneById(@PathVariable("idPost") long idPost,
			@RequestBody PostazioneDto postazioneDto) {
		
		if(idPost != postazioneDto.getId()) {
			return new ResponseEntity<>("le id non coincidono", HttpStatus.BAD_REQUEST);
		}

		Postazione postazioneNuova = postazioneDto.toPostazione();
		try {
//		Postazione p =	postazioneService.updatePostazioneOld(idPost, numeroMaxOcc, idEdificio);
			Postazione postazioneCambiata = postazioneService.updatePostazione(postazioneNuova);
			PostazioneDto pDto = PostazioneDto.fromPostazione(postazioneCambiata);

			return new ResponseEntity<>(pDto, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}
}
