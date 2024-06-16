package it.uniroma3.siwLeague.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwLeague.model.Torneo;

public interface TorneoRepository extends CrudRepository<Torneo, Long> {
	
	public List<Torneo> findByIscrizioneInCorsoOrderByNomeAsc(boolean iscrizioneInCorso);
}
