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

import it.uniroma3.siwLeague.model.Partita;
import it.uniroma3.siwLeague.model.Squadra;
import it.uniroma3.siwLeague.service.GiocatoreService;
import it.uniroma3.siwLeague.service.PartitaService;
import it.uniroma3.siwLeague.service.SquadraService;

@Controller
public class PartitaController {
	
	@Autowired
	private PartitaService partitaService;
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private GiocatoreService giocatoreService;
	
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
		
		Squadra squadraCasa = partita.getSquadraCasa();		
		Squadra squadraFuoriCasa = partita.getSquadraFuoriCasa();
		
		this.partitaService.save(partita);
		
		squadraCasa.setPunti();
		squadraFuoriCasa.setPunti();
		
		this.squadraService.save(squadraCasa);
		this.squadraService.save(squadraFuoriCasa);

		return "redirect:/formAddMarcatori/"+partita.getIdPartita();
	}
	
	@GetMapping(value = "/formAddMarcatori/{idPartita}")
	public String getFormAddMarcatori(@PathVariable("idPartita") Long idPartita, Model model){
		Partita partita = this.partitaService.findPartitaByIdPartita(idPartita);
		model.addAttribute("partita", partita);
		return "partita/formAddMarcatori.html";
	}
	
	@GetMapping(value = "/addMarcatori/{idPartita}")
	public String postAddMarcatori(@PathVariable("idPartita")Long idPartita, @RequestParam("giocatore") List<Long> giocatori ) {
		Partita partita = this.partitaService.findPartitaByIdPartita(idPartita);
		for(Long idGiocatore : giocatori) {
			
			partita.getMarcatori().add(this.giocatoreService.findGiocatoreByIdGiocatore(idGiocatore));
		}
		
		this.partitaService.save(partita);
		return "redirect:/partita/"+idPartita;
	}
	
}
