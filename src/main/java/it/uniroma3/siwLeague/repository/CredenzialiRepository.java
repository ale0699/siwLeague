package it.uniroma3.siwLeague.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwLeague.model.Credenziali;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {
	
	public Credenziali findByUsername(String username);
	
	public boolean existsByUsername(String username);
}	
