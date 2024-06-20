package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwLeague.model.Squadra;
import it.uniroma3.siwLeague.service.PartitaService;
import it.uniroma3.siwLeague.service.SquadraService;
import it.uniroma3.siwLeague.service.TorneoService;

@Controller
public class TorneoController {
	
	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private PartitaService partitaService;
	
	@GetMapping(value = "/tornei")
	public String getAllTornei(Model model) {
		model.addAttribute("tornei", this.torneoService.findAllTornei());
		return "torneo/tornei.html";
	}
	
	@GetMapping(value = "/torneo/{idTorneo}")
	public String getTorneo(@PathVariable("idTorneo")Long idTorneo, Model model) {
		model.addAttribute("partite", this.partitaService.findAllPartiteByIdTorneo(idTorneo));
		model.addAttribute("squadrePartecipanti", this.squadraService.findSquadrePartecipantiTorneoByIdTorneo(idTorneo));
		model.addAttribute("torneo", this.torneoService.getTorneoByIdTorneo(idTorneo));
		
		//aggiorno la persistenza ogni volta che visualizzo un torneo
		for(Squadra squadra : this.squadraService.findSquadrePartecipantiTorneoByIdTorneo(idTorneo)) {
			
			squadra.setPunti();
			this.squadraService.save(squadra);
		}
		
		return "torneo/torneo.html";
	}
	
	@GetMapping(value = "/selectTorneo")
	public String getSelectTorneo(Model model) {
		model.addAttribute("torneiAperteIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(true));
		model.addAttribute("torneiChiuseIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(false));
		return "torneo/selectTorneo.html";
	}
}
