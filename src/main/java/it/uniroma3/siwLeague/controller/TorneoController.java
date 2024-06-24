package it.uniroma3.siwLeague.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwLeague.model.Giocatore;
import it.uniroma3.siwLeague.model.Partita;
import it.uniroma3.siwLeague.model.Squadra;
import it.uniroma3.siwLeague.model.Torneo;
import it.uniroma3.siwLeague.service.GiocatoreService;
import it.uniroma3.siwLeague.service.PartitaService;
import it.uniroma3.siwLeague.service.SquadraService;
import it.uniroma3.siwLeague.service.TorneoService;

@Controller
public class TorneoController {

	@Autowired
	private TorneoService torneoService;

	@Autowired
	private SquadraService squadraService;

	@Autowired
	private PartitaService partitaService;

	@Autowired
	private GiocatoreService giocatoreService;

	@GetMapping(value = "/tornei")
	public String getAllTornei(Model model) {
		model.addAttribute("tornei", this.torneoService.findAllTornei());
		return "torneo/tornei.html";
	}

	@GetMapping(value = "/torneo/{idTorneo}")
	public String getTorneo(@PathVariable("idTorneo") Long idTorneo, Model model) {
		List<Partita> tuttePartiteTorneo = this.partitaService.findAllPartiteByIdTorneo(idTorneo);
		List<Squadra> squadrePartecipantiTorneo = this.squadraService.findSquadrePartecipantiTorneoByIdTorneo(idTorneo);
		List<Giocatore> giocatoriTorneo = this.giocatoreService.findGiocatoriBySquadraTorneoIdTorneo(idTorneo);
		Torneo torneo = this.torneoService.findTorneoByIdTorneo(idTorneo);

		//Ordinamento giocatoriTorneo per golSegnati
		giocatoriTorneo.sort(Comparator.comparingInt(Giocatore::getGolSegnati).reversed());

		//Ordinamento squadrePartecipantiTorneo per punti
		squadrePartecipantiTorneo.sort(Comparator.comparingInt(Squadra::getPunti).reversed());

		model.addAttribute("torneo", torneo);
		model.addAttribute("partite", tuttePartiteTorneo);
		model.addAttribute("squadrePartecipanti", squadrePartecipantiTorneo);
		model.addAttribute("marcatori", giocatoriTorneo);
		return "torneo/torneo.html";
	}

	@GetMapping(value = "/selectTorneo")
	public String getSelectTorneo(Model model) {
		model.addAttribute("torneiAperteIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(true));
		model.addAttribute("torneiChiuseIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(false));
		return "torneo/selectTorneo.html";
	}
	
	@GetMapping(value = "/admin/formAddTorneo")
	public String getFormAddTorneo(Model model) {
		model.addAttribute(new Torneo());
		return "torneo/formAddTorneo.html";
	}
	
	@PostMapping(value = "/admin/addTorneo")
	public String postAddTorneo(@ModelAttribute Torneo torneo, @RequestParam("logo-image")MultipartFile logo) throws IOException {
		
		
    	if (!logo.isEmpty()) { // Verifica se il file è vuoto

            Path fileNameAndPath = Paths.get("src/main/resources/static/images/tornei", logo.getOriginalFilename());
            Files.write(fileNameAndPath, logo.getBytes());
            torneo.setLogo("/images/tornei/" + logo.getOriginalFilename());
        }
		
		torneo.setIscrizioneInCorso(true);
		torneo.setSvolgimentoInCorso(false);
		this.torneoService.save(torneo);
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping(value = "/admin/torneoTerminaIscrizioni/{idTorneo}")
	public String getTorneoTerminaIscrizioni(@PathVariable("idTorneo")Long idTorneo) {
		Torneo torneo = this.torneoService.findTorneoByIdTorneo(idTorneo);
		torneo.setIscrizioneInCorso(false);
		torneo.setSvolgimentoInCorso(true);
		this.torneoService.save(torneo);
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping(value = "/admin/torneoTerminaCompetizione/{idTorneo}")
	public String getTorneoTerminaCompetizione(@PathVariable("idTorneo")Long idTorneo) {
		Torneo torneo = this.torneoService.findTorneoByIdTorneo(idTorneo);
		torneo.setSvolgimentoInCorso(false);
		this.torneoService.save(torneo);
		return "redirect:/admin/dashboard";
	}
}
