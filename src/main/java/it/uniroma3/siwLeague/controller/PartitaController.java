package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwLeague.model.Partita;
import it.uniroma3.siwLeague.service.PartitaService;
import it.uniroma3.siwLeague.service.SquadraService;

@Controller
public class PartitaController {
	
	@Autowired
	private PartitaService partitaService;
	
	@Autowired
	private SquadraService squadraService;
	
	@GetMapping(value = "/partita/{idPartita}")
	public String getPartita(@PathVariable("idPartita")Long idPartita, Model model) {
		model.addAttribute("partita", this.partitaService.findPartitaByIdPartita(idPartita));
		return "partita/partita.html";
	}
	
	@GetMapping(value = "/formAddPartita/{idTorneo}")
	public String getFormAddPartita(@PathVariable("idTorneo")Long idTorneo, Model model) {
		model.addAttribute(new Partita());
		model.addAttribute("squadre", this.squadraService.findSquadrePartecipantiTorneoByIdTorneo(idTorneo));
		return "partita/formAddPartita.html";
	}
	
	@PostMapping(value = "/addPartita")
	public String postAddPartita(@ModelAttribute Partita partita) {
		partita.setTorneo(partita.getSquadraCasa().getTorneo());
		this.partitaService.save(partita);
		return "redirect:/partita/"+partita.getIdPartita();
	}
	
}
