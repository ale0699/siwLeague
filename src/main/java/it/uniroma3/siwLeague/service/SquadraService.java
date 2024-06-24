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
	
	public List<Squadra> findAllSquadre(){
		return (List<Squadra>) this.squadraRepository.findAll();
	}
	
	public Squadra findSquadraByIdSquadra(Long idSquadra) {
		
		return this.squadraRepository.findById(idSquadra).get();
	}
	
	public List<Squadra> findSquadreIdGestoreSquadra(Long idGestoreSquadra){
	
		return this.squadraRepository.findByTeamManagerIdGestoreSquadra(idGestoreSquadra);
	}
	
	public List<Squadra> findSquadrePartecipantiTorneoByIdTorneo(Long idTorneo){
		
		return this.squadraRepository.findByTorneoIdTorneo(idTorneo);
	}
	
	public boolean existsSquadraByNomeAndTorneo(String nome, Long idTorneo) {
		
		return this.squadraRepository.existsByNomeIgnoreCaseAndTorneoIdTorneo(nome, idTorneo);
	}
	
	public void save(Squadra squadra) {
		
		this.squadraRepository.save(squadra);
	}
	
	public void remove(Squadra squadra) {
		
		this.squadraRepository.delete(squadra);
	}
}
