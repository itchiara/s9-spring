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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.be.controller.api.dto.PostazioneDto;
import it.epicode.be.model.Postazione;
import it.epicode.be.model.Tipo;
import it.epicode.be.service.abstractions.AbstractPostazioneService;

@RestController
@RequestMapping("/api/postazioni")
public class PostazioneRestController {

	@Autowired
	private AbstractPostazioneService postazioneService;

	@GetMapping
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
}
