package it.uniroma3.siwLeague.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwLeague.model.Torneo;
import it.uniroma3.siwLeague.repository.TorneoRepository;

@Service
public class TorneoService {
	
	@Autowired
	private TorneoRepository torneoRepository;
	
	public List<Torneo> findAllTornei(){
		
		return (List<Torneo>) this.torneoRepository.findAll();
	}
	
	public List<Torneo> findTorneiByIscrizioneInCorso(boolean iscrizioneInCorso){
		
		return this.torneoRepository.findByIscrizioneInCorsoOrderByNomeAsc(iscrizioneInCorso);
	}
}
