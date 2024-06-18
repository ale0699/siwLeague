package it.uniroma3.siwLeague.service;

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
	
	public void save(Partita partita) {
		
		this.partitaRepository.save(partita);
	}
}
