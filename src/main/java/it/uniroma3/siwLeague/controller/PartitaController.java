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
import it.uniroma3.siwLeague.model.Squadra;
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
	
	@GetMapping(value = "/matches/{idPartita}")
	public String getPartita(@PathVariable("idPartita")Long idPartita, Model model) {
		Partita partita = this.partitaService.findPartitaByIdPartita(idPartita);
		model.addAttribute("partita", partita);
		model.addAttribute("giocatoriCasa", this.giocatoreService.findGiocatoriBySquadraIdSquadra(partita.getSquadraCasa().getIdSquadra()));
		model.addAttribute("giocatoriFuoriCasa", this.giocatoreService.findGiocatoriBySquadraIdSquadra(partita.getSquadraFuoriCasa().getIdSquadra()));
		return "partita/partita.html";
	}
	
	@GetMapping(value = "/admin/matches/edit/tournaments/{idTorneo}")
	public String getFormAddPartita(@PathVariable("idTorneo")Long idTorneo, Model model) {
		model.addAttribute(new Partita());
		model.addAttribute("torneo", this.torneoService.findTorneoByIdTorneo(idTorneo));
		model.addAttribute("partite", this.partitaService.findAllPartiteByIdTorneo(idTorneo));
		model.addAttribute("squadre", this.squadraService.findSquadrePartecipantiTorneoByIdTorneo(idTorneo));
		return "partita/formManagePartita.html";
	}
	
	@PostMapping(value = "/admin/matches/add")
	public String postAddPartita(@ModelAttribute Partita partita) {
		partita.setTorneo(partita.getSquadraCasa().getTorneo());
		
		Squadra squadraCasa = partita.getSquadraCasa();
		Squadra squadraFuoriCasa = partita.getSquadraFuoriCasa();
		int golSquadraCasa = partita.getGolSquadraCasa();
		int golSquadraFuoriCasa = partita.getGolSquadraFuoriCasa();
		
		if(partita.getSquadraVincente()!=null) { //se non c'è un pareggio
			
			if(partita.getSquadraVincente().equals(squadraCasa)) {
				
				squadraCasa.setVittoria(golSquadraCasa, golSquadraFuoriCasa);
				squadraFuoriCasa.setSconfitta(golSquadraFuoriCasa, golSquadraCasa);
			}
			else {
				
				squadraCasa.setSconfitta(golSquadraCasa, golSquadraFuoriCasa);
				squadraFuoriCasa.setVittoria(golSquadraFuoriCasa, golSquadraCasa);
			}
		}
		else {
			
			squadraCasa.setPareggio(golSquadraCasa);
			squadraFuoriCasa.setPareggio(golSquadraFuoriCasa);
		}
		
		
		this.partitaService.save(partita);
		return "redirect:/admin/matches/"+partita.getIdPartita()+"/scorers/addForm";
	}
	
	@GetMapping(value = "/admin/matches/{idPartita}/scorers/addForm")
	public String getFormAddMarcatori(@PathVariable("idPartita") Long idPartita, Model model){
		Partita partita = this.partitaService.findPartitaByIdPartita(idPartita);
		model.addAttribute("partita", partita);
		return "partita/formAddMarcatori.html";
	}
	
	@GetMapping(value = "/admin/matches/{idPartita}/scorers/add")
	public String postAddMarcatori(@PathVariable("idPartita")Long idPartita, @RequestParam(value = "giocatore", required = false) List<Long> giocatori, @RequestParam(value = "minuto", required = false) List<Integer> minuti ) {
		Partita partita = this.partitaService.findPartitaByIdPartita(idPartita);
		
		if(giocatori!=null) {
			
			int i = 0;
			for(Long idGiocatore : giocatori) {
				
				Giocatore giocatoreCorrente = this.giocatoreService.findGiocatoreByIdGiocatore(idGiocatore);
				giocatoreCorrente.setGolSegnato();
				partita.getMarcatori().put(minuti.get(i), giocatoreCorrente);
				i++;
			}
			
			this.partitaService.save(partita);
		}
		
		return "redirect:/admin/matches/edit/tournaments/"+partita.getTorneo().getIdTorneo();
	}
	
	@GetMapping(value = "/admin/matches/remove/{idPartita}")
	public String getRemovePartita(@PathVariable("idPartita")Long idPartita) {
		Partita partita = this.partitaService.findPartitaByIdPartita(idPartita);
		
		Squadra squadraCasa = partita.getSquadraCasa();
		Squadra squadraFuoriCasa = partita.getSquadraFuoriCasa();
		int golSquadraCasa = partita.getGolSquadraCasa();
		int golSquadraFuoriCasa = partita.getGolSquadraFuoriCasa();
		
		if(partita.getSquadraVincente()!=null) { //se non c'è un pareggio
			
			if(partita.getSquadraVincente().equals(squadraCasa)) {
				
				squadraCasa.removeVittoria(golSquadraCasa, golSquadraFuoriCasa);
				squadraFuoriCasa.removeSconfitta(golSquadraFuoriCasa, golSquadraCasa);
			}
			else {
				
				squadraCasa.removeSconfitta(golSquadraCasa, golSquadraFuoriCasa);
				squadraFuoriCasa.removeVittoria(golSquadraFuoriCasa, golSquadraCasa);
			}
		}
		else {
			
			squadraCasa.removePareggio(golSquadraCasa);
			squadraFuoriCasa.removePareggio(golSquadraFuoriCasa);
		}
		
		for(Giocatore giocatore : partita.getMarcatori().values()) {
			
			giocatore.removeGolSegnato();
		}
		
		this.partitaService.remove(partita);
		return "redirect:/admin/matches/edit/tournaments/"+partita.getTorneo().getIdTorneo();
	}
	
}
