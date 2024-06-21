package it.uniroma3.siwLeague.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwLeague.model.Giocatore;

public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {
	
	public List<Giocatore> findBySquadraIdSquadraOrderByNumeroMaglia(Long idSquadra);
	
	public List<Giocatore> findBySquadraTorneoIdTorneoOrderByGolSegnatiDesc(Long idTorneo);
}
