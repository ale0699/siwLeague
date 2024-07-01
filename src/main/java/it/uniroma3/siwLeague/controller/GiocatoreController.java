package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwLeague.controller.validator.GiocatoreValidator;
import it.uniroma3.siwLeague.model.Giocatore;
import it.uniroma3.siwLeague.service.GiocatoreService;
import it.uniroma3.siwLeague.service.SquadraService;
import jakarta.validation.Valid;

@Controller
public class GiocatoreController {

	@Autowired
	private SquadraService squadraService;

	@Autowired
	private GiocatoreService giocatoreService;
	
	@Autowired
	private GiocatoreValidator giocatoreValidator;

	@PostMapping(value = "manager/players/add")
	public String postAddGiocatoriSquadra(@RequestParam("idSquadra") Long idSquadra,@Valid @ModelAttribute Giocatore giocatore, BindingResult bindingResult, Model model) {
		giocatore.setSquadra(this.squadraService.findSquadraByIdSquadra(idSquadra));
		this.giocatoreValidator.validate(giocatore, bindingResult);
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("squadra", this.squadraService.findSquadraByIdSquadra(idSquadra));
			model.addAttribute("giocatori", this.giocatoreService.findGiocatoriBySquadraIdSquadra(idSquadra));
			model.addAttribute("giocatore", giocatore);
			return "squadra/formManageSquadra.html";
		}
		
		this.giocatoreService.save(giocatore);
		return "redirect:/manager/teams/edit/" + idSquadra;
	}
	
	@GetMapping(value = "manager/players/remove/{idGiocatore}")
	public String getRemoveGiocatore(@PathVariable("idGiocatore") Long idGiocatore) {
		Giocatore giocatore = this.giocatoreService.findGiocatoreByIdGiocatore(idGiocatore);
		this.giocatoreService.remove(giocatore);
		return "redirect:/manager/teams/edit/" + giocatore.getSquadra().getIdSquadra();
	}
}
