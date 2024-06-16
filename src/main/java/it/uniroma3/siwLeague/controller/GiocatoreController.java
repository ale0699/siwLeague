package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwLeague.service.GiocatoreService;
import it.uniroma3.siwLeague.service.SquadraService;

@Controller
public class GiocatoreController {
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@GetMapping(value = "/formAddPlayersSquad/{idSquadra}")
	public String getformAddPlayersSquad(@PathVariable("idSquadra")Long idSquadra, Model model) {
		model.addAttribute("squadra", this.squadraService.findSquadraByIdSquadra(idSquadra));
		model.addAttribute("giocatori", this.giocatoreService.findGiocatoriBySquadraIdSquadra(idSquadra));
		return "giocatori/formAddPlayersSquad.html";
	}
}
