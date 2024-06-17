package it.uniroma3.siwLeague.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwLeague.model.Squadra;
import it.uniroma3.siwLeague.model.Torneo;
import it.uniroma3.siwLeague.service.GiocatoreService;
import it.uniroma3.siwLeague.service.SquadraService;
import it.uniroma3.siwLeague.service.TorneoService;

@Controller
public class SquadraController {
	
	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@GetMapping(value = "/squadra/{idSquadra}")
	public String getSquadra(@PathVariable("idSquadra")Long idSquadra, Model model){
		model.addAttribute("squadra", this.squadraService.findSquadraByIdSquadra(idSquadra));
		model.addAttribute("giocatori", this.giocatoreService.findGiocatoriBySquadraIdSquadra(idSquadra));
		return "squadra/squadra.html";
	}
	
	@GetMapping(value = "/formAddSquadra/{idTorneo}")
	public String getFormAddSquadra(@PathVariable("idTorneo")Long idTorneo, Model model) throws Exception {
		Torneo torneo = this.torneoService.getTorneoByIdTorneo(idTorneo);
		
		//controllo che il torneo sia effettivamente aperto alle iscrizioni
		if (torneo.isIscrizioneInCorso()) {
			
			model.addAttribute("torneo", torneo);
			model.addAttribute(new Squadra());
			return "squadra/formAddSquadra.html";
		}
		else {
			model.addAttribute("error", "Non puoi iscrivere la tua squadra al torneo "+torneo.getNome());
			model.addAttribute("torneiAperteIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(true));
			model.addAttribute("torneiChiuseIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(false));
			return "torneo/selectTorneo.html";
		}
	}
	
	@PostMapping(value = "addSquadra")
	public String postAddSquadra(@RequestParam("idTorneo")Long idTorneo, @ModelAttribute Squadra squadra) {
		Torneo torneo = this.torneoService.getTorneoByIdTorneo(idTorneo);
		squadra.setTorneo(torneo);
		this.squadraService.save(squadra); //forse non qua
		return "redirect:/formAddPlayersSquad/"+squadra.getIdSquadra();
	}
	
}
