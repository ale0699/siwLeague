package it.uniroma3.siwLeague.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwLeague.model.Giocatore;
import it.uniroma3.siwLeague.repository.GiocatoreRepository;

@Service
public class GiocatoreService {
	
	@Autowired
	private GiocatoreRepository giocatoreRepository;
	
	public Giocatore findGiocatoreByIdGiocatore(Long idGiocatore) {
		
		return this.giocatoreRepository.findById(idGiocatore).get();
	}
	
	public List<Giocatore> findGiocatoriBySquadraIdSquadra(Long idSquadra){
		
		return this.giocatoreRepository.findBySquadraIdSquadraOrderByNumeroMaglia(idSquadra);
	}
	
	public void save(Giocatore giocatore) {
		
		this.giocatoreRepository.save(giocatore);
	}
}
