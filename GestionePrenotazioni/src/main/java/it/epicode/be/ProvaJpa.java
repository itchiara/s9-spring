package it.epicode.be;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.epicode.be.persistence.EdificioRepository;
import it.epicode.be.persistence.PrenotazioneRepository;

@Component
public class ProvaJpa implements CommandLineRunner{

	@Autowired
	private PrenotazioneRepository prenotRepo;
	
	@Autowired
	private EdificioRepository edRepo;

	@Override
	public void run(String... args) throws Exception {
		
//		List<Edificio> le = edRepo.findAll();
//		for(Edificio e : le) {
//			System.out.println(e.getListaPostazioni().size());
//		}
		
//		Optional<Edificio> ed = edRepo.findById(1l);
//		if(ed.isPresent()) {
//			Edificio e = ed.get();
//			System.out.println(e.getClass().getName());
//			System.out.println(e.getNome());
//			List<Postazione> listaPost = e.getListaPostazioni();
//			for(Postazione p : listaPost) {
//				System.out.println(p.getId());
//			}
//		} 
		
		
	}
}
