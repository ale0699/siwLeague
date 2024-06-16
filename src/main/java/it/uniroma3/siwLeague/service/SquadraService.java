package it.uniroma3.siwLeague.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwLeague.model.Squadra;
import it.uniroma3.siwLeague.repository.SquadraRepository;

@Service
public class SquadraService {
	
	@Autowired
	private SquadraRepository squadraRepository;
	
	public Squadra findSquadraByIdSquadra(Long idSquadra) {
		
		return this.squadraRepository.findById(idSquadra).get();
	}
	
	public List<Squadra> findSquadrePartecipantiTorneoByIdTorneo(Long idTorneo){
		
		return this.squadraRepository.findByTorneoIdTorneo(idTorneo);
	}
	
	public void save(Squadra squadra) {
		
		this.squadraRepository.save(squadra);
	}
}
