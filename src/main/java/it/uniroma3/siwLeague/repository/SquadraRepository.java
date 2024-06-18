package it.uniroma3.siwLeague.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwLeague.model.Squadra;

public interface SquadraRepository extends CrudRepository<Squadra, Long> {
	
	public List<Squadra> findByTorneoIdTorneoOrderByPuntiDesc (Long idTorneo);
}
