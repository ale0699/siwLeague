package it.uniroma3.siwLeague.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.repository.GestoreSquadraRepository;

@Service
public class GestoreSquadraService {
	
	@Autowired
	private GestoreSquadraRepository gestoreSquadraRepository;
	
	public void save(GestoreSquadra gestoreSquadra) {
		
		this.gestoreSquadraRepository.save(gestoreSquadra);
	}
}
