package it.uniroma3.siwLeague.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwLeague.model.Credenziali;
import it.uniroma3.siwLeague.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	
	@Autowired
	private CredenzialiRepository credenzialiRepository;
	
	public Credenziali findCredenzialiByUsername(String username) {
		
		return this.credenzialiRepository.findByUsername(username);
	}
	
	public boolean existsCredenzialiByUsername(String username) {
		
		return this.credenzialiRepository.existsByUsername(username);
	}
}
