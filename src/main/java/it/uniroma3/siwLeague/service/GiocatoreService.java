package it.uniroma3.siwLeague.service;

import java.time.LocalDate;
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
	
	public List<Giocatore> findGiocatoriBySquadraTorneoIdTorneo(Long idTorneo){
		
		return this.giocatoreRepository.findBySquadraTorneoIdTorneo(idTorneo);
	}
	
	public List<Giocatore> findGiocatoriBySquadraIdSquadra(Long idSquadra){
		
		return this.giocatoreRepository.findBySquadraIdSquadraOrderByNumeroMaglia(idSquadra);
	}
	
	public boolean existsGiocatoreByNumeroMagliaAndSquadra(Integer numeroMaglia, Long idSquadra) {
		
		return this.giocatoreRepository.existsByNumeroMagliaAndSquadraIdSquadra(numeroMaglia, idSquadra);
	}
	
	public boolean existsGiocatoreByNomeAndCognomeAndDataNascitaAndSquadra(String nome, String cognome, LocalDate dataNascita,Long idSquadra) {
		
		return this.giocatoreRepository.existsByNomeAndCognomeAndDataNascitaAndSquadraIdSquadra(nome, cognome, dataNascita, idSquadra);
	}
	
	public void save(Giocatore giocatore) {
		
		this.giocatoreRepository.save(giocatore);
	}

	public void remove(Giocatore giocatore) {
		
		this.giocatoreRepository.delete(giocatore);
	}
}
