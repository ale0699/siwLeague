package it.uniroma3.siwLeague.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwLeague.model.Giocatore;
import it.uniroma3.siwLeague.model.Partita;
import it.uniroma3.siwLeague.service.GiocatoreService;
import it.uniroma3.siwLeague.service.PartitaService;
import it.uniroma3.siwLeague.service.SquadraService;
import it.uniroma3.siwLeague.service.TorneoService;

@Controller
public class PartitaController {
	
	@Autowired
	private PartitaService partitaService;
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@Autowired
	private TorneoService torneoService;
	
	@GetMapping(value = "/partita/{idPartita}")
	public String getPartita(@PathVariable("idPartita")Long idPartita, Model model) {
		Partita partita = this.partitaService.findPartitaByIdPartita(idPartita);
		model.addAttribute("partita", partita);
		model.addAttribute("giocatoriCasa", this.giocatoreService.findGiocatoriBySquadraIdSquadra(partita.getSquadraCasa().getIdSquadra()));
		model.addAttribute("giocatoriFuoriCasa", this.giocatoreService.findGiocatoriBySquadraIdSquadra(partita.getSquadraFuoriCasa().getIdSquadra()));
		return "partita/partita.html";
	}
	
	@GetMapping(value = "/formAddPartita/{idTorneo}")
	public String getFormAddPartita(@PathVariable("idTorneo")Long idTorneo, Model model) {
		model.addAttribute(new Partita());
		model.addAttribute("torneo", this.torneoService.findTorneoByIdTorneo(idTorneo));
		model.addAttribute("partite", this.partitaService.findAllPartiteByIdTorneo(idTorneo));
		model.addAttribute("squadre", this.squadraService.findSquadrePartecipantiTorneoByIdTorneo(idTorneo));
		return "partita/formAddPartita.html";
	}
	
	@PostMapping(value = "/addPartita")
	public String postAddPartita(@ModelAttribute Partita partita) {
		
		partita.setTorneo(partita.getSquadraCasa().getTorneo());
		
		this.partitaService.save(partita);
		return "redirect:/formAddMarcatori/"+partita.getIdPartita();
	}
	
	@GetMapping(value = "/formAddMarcatori/{idPartita}")
	public String getFormAddMarcatori(@PathVariable("idPartita") Long idPartita, Model model){
		Partita partita = this.partitaService.findPartitaByIdPartita(idPartita);
		model.addAttribute("partita", partita);
		return "partita/formAddMarcatori.html";
	}
	
	@GetMapping(value = "/addMarcatori/{idPartita}")
	public String postAddMarcatori(@PathVariable("idPartita")Long idPartita, @RequestParam("giocatore") List<Long> giocatori, @RequestParam("minuto") List<Integer> minuti ) {
		Partita partita = this.partitaService.findPartitaByIdPartita(idPartita);
		
		int i = 0;
		for(Long idGiocatore : giocatori) {
			
			Giocatore giocatoreCorrente = this.giocatoreService.findGiocatoreByIdGiocatore(idGiocatore);
			partita.getMarcatori().put(minuti.get(i), giocatoreCorrente);
			i++;
		}
		
		this.partitaService.save(partita);
		return "redirect:/formAddPartita/"+partita.getTorneo().getIdTorneo();
	}
	
}
