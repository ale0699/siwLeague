package it.uniroma3.siwLeague.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwLeague.model.Credenziali;
import it.uniroma3.siwLeague.model.GestoreSquadra;

public interface GestoreSquadraRepository extends CrudRepository<GestoreSquadra, Long> {
	
	public GestoreSquadra findByCredenziali(Credenziali credenziali);
}
