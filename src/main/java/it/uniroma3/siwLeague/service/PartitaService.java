package it.uniroma3.siwLeague.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwLeague.model.Partita;
import it.uniroma3.siwLeague.repository.PartitaRepository;

@Service
public class PartitaService {
	
	@Autowired
	private PartitaRepository partitaRepository;
	
	public Partita findPartitaByIdPartita(Long idPartita) {
		
		return this.partitaRepository.findById(idPartita).get();
	}
	
	public List<Partita> findAllPartiteByIdTorneo(Long idTorneo){
		
		return this.partitaRepository.findAllByTorneoIdTorneoOrderByNumeroGiornata(idTorneo);
	}
	
	public void save(Partita partita) {
		
		this.partitaRepository.save(partita);
	}

	public void remove(Partita partita) {

		this.partitaRepository.delete(partita);
	}
	
	public List<Partita> findByMarcatoriIdGiocatore(Long idGiocatore){
		
		return this.partitaRepository.findByMarcatoriIdGiocatore(idGiocatore);
	}
	
	public List<Partita> findBySquadraCasaOrSquadraFuoriCasa(Long idSquadraCasa, Long idSquadraFuoriCasa){
		
		return this.partitaRepository.findBySquadraCasaIdSquadraOrSquadraFuoriCasaIdSquadra(idSquadraCasa, idSquadraFuoriCasa);
	}
}
