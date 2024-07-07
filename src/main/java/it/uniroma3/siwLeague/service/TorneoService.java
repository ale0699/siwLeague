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
	
	public Torneo findTorneoByIdTorneo(Long idTorneo) {
		
		return this.torneoRepository.findById(idTorneo).get();
	}
	
	public List<Torneo> findAllTornei(){
		
		return (List<Torneo>) this.torneoRepository.findAll();
	}
	
	public List<Torneo> findTorneiByIscrizioneInCorso(boolean iscrizioneInCorso){
		
		return this.torneoRepository.findByIscrizioneInCorso(iscrizioneInCorso);
	}
	
	public void save(Torneo torneo) {
		
		this.torneoRepository.save(torneo);
	}
}
