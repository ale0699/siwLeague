package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@GetMapping(value = "/formAddGiocatoriSquadra/{idSquadra}")
	public String getFormAddGiocatoriSquadra(@PathVariable("idSquadra") Long idSquadra, Model model) {
		model.addAttribute("squadra", this.squadraService.findSquadraByIdSquadra(idSquadra));
		model.addAttribute("giocatori", this.giocatoreService.findGiocatoriBySquadraIdSquadra(idSquadra));
		model.addAttribute(new Giocatore());
		return "giocatori/formAddGiocatoriSquadra.html";
	}

	@PostMapping(value = "addGiocatoriSquadra")
	public String postAddGiocatoriSquadra(@RequestParam("idSquadra") Long idSquadra,
			@ModelAttribute Giocatore giocatore) {
		giocatore.setSquadra(this.squadraService.findSquadraByIdSquadra(idSquadra));
		this.giocatoreService.save(giocatore);
		return "redirect:/formAddGiocatoriSquadra/" + idSquadra;
	}

	@GetMapping(value = "/removeGiocatoreSquadra/{idGiocatore}")
	public String getRemoveGiocatoreSquadra(@PathVariable("idGiocatore") Long idGiocatore) {
		Giocatore giocatore = this.giocatoreService.findGiocatoreByIdGiocatore(idGiocatore);
		this.giocatoreService.remove(giocatore);
		return "redirect:/squadra/" + giocatore.getSquadra().getIdSquadra();
	}
	
	@GetMapping(value = "/removeGiocatore/{idGiocatore}")
	public String getRemoveGiocatore(@PathVariable("idGiocatore") Long idGiocatore) {
		Giocatore giocatore = this.giocatoreService.findGiocatoreByIdGiocatore(idGiocatore);
		this.giocatoreService.remove(giocatore);
		return "redirect:/formAddGiocatoriSquadra/" + giocatore.getSquadra().getIdSquadra();
	}
}
