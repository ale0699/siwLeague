package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwLeague.model.Partita;
import it.uniroma3.siwLeague.service.SquadraService;

@Controller
public class PartitaController {
	
	@Autowired
	private SquadraService squadraService;
	
	@GetMapping(value = "/formAddPartita/{idTorneo}")
	public String getFormAddPartita(@PathVariable("idTorneo")Long idTorneo, Model model) {
		model.addAttribute(new Partita());
		model.addAttribute("squadre", this.squadraService.findSquadrePartecipantiTorneoByIdTorneo(idTorneo));
		return "partita/formAddPartita.html";
	}
}
