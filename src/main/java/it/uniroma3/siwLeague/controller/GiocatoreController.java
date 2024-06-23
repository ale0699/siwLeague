package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwLeague.model.Giocatore;
import it.uniroma3.siwLeague.service.GiocatoreService;
import it.uniroma3.siwLeague.service.SquadraService;

@Controller
public class GiocatoreController {

	@Autowired
	private SquadraService squadraService;

	@Autowired
	private GiocatoreService giocatoreService;

	@PostMapping(value = "addGiocatoriSquadra")
	public String postAddGiocatoriSquadra(@RequestParam("idSquadra") Long idSquadra,
			@ModelAttribute Giocatore giocatore) {
		giocatore.setSquadra(this.squadraService.findSquadraByIdSquadra(idSquadra));
		this.giocatoreService.save(giocatore);
		return "redirect:/formManageSquadra/" + idSquadra;
	}
	
	@GetMapping(value = "/removeGiocatore/{idGiocatore}")
	public String getRemoveGiocatore(@PathVariable("idGiocatore") Long idGiocatore) {
		Giocatore giocatore = this.giocatoreService.findGiocatoreByIdGiocatore(idGiocatore);
		this.giocatoreService.remove(giocatore);
		return "redirect:/formManageSquadra/" + giocatore.getSquadra().getIdSquadra();
	}
}
