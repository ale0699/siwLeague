package it.uniroma3.siwLeague.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwLeague.model.Partita;

public interface PartitaRepository extends CrudRepository<Partita, Long> {
	
	public List<Partita> findAllByTorneoIdTorneo(Long idTorneo);

}
