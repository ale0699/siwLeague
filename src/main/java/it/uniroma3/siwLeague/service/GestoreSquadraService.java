package it.uniroma3.siwLeague.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwLeague.model.Credenziali;
import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.repository.GestoreSquadraRepository;

@Service
public class GestoreSquadraService {
	
	@Autowired
	private GestoreSquadraRepository gestoreSquadraRepository;
	
	public GestoreSquadra findGestoreSquadraById(Long idGestoreSquadra) {
		
		return this.gestoreSquadraRepository.findById(idGestoreSquadra).get();
	}
	
	public GestoreSquadra findGestoreSquadraByCredenziali(Credenziali credenziali) {
		
		return this.gestoreSquadraRepository.findByCredenziali(credenziali);
	}
	
	public void save(GestoreSquadra gestoreSquadra) {
		
		this.gestoreSquadraRepository.save(gestoreSquadra);
	}
}
