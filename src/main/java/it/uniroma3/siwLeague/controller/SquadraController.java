package it.uniroma3.siwLeague.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwLeague.controller.validator.SquadraValidator;
import it.uniroma3.siwLeague.model.Credenziali;
import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.model.Giocatore;
import it.uniroma3.siwLeague.model.Squadra;
import it.uniroma3.siwLeague.model.Torneo;
import it.uniroma3.siwLeague.service.CredenzialiService;
import it.uniroma3.siwLeague.service.GestoreSquadraService;
import it.uniroma3.siwLeague.service.GiocatoreService;
import it.uniroma3.siwLeague.service.PartitaService;
import it.uniroma3.siwLeague.service.SquadraService;
import it.uniroma3.siwLeague.service.TorneoService;
import jakarta.validation.Valid;

@Controller
public class SquadraController {
	
	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@Autowired
	private SquadraValidator squadraValidator;
	
	@Autowired
	private CredenzialiService credenzialiService;
	
	@Autowired
	private GestoreSquadraService gestoreSquadraService;
	
	@Autowired
	private PartitaService partitaService;
	
	@GetMapping(value = "/teams/{idSquadra}")
	public String getSquadra(@PathVariable("idSquadra")Long idSquadra, Model model){
		model.addAttribute("partiteGiocate", this.partitaService.findBySquadraCasaOrSquadraFuoriCasa(idSquadra, idSquadra));
		model.addAttribute("squadra", this.squadraService.findSquadraByIdSquadra(idSquadra));
		model.addAttribute("giocatori", this.giocatoreService.findGiocatoriBySquadraIdSquadra(idSquadra));
		return "squadra/squadra.html";
	}
	
	@GetMapping(value = "manager/teams/add/tournaments/{idTorneo}")
	public String getFormAddSquadra(@PathVariable("idTorneo")Long idTorneo, Model model) throws Exception {
		Torneo torneo = this.torneoService.findTorneoByIdTorneo(idTorneo);
		
		//controllo che il torneo sia effettivamente aperto alle iscrizioni
		if (torneo.isIscrizioneInCorso()) {
			
			model.addAttribute("torneo", torneo);
			model.addAttribute(new Squadra());
			return "squadra/formAddSquadra.html";
		}
		else {

			model.addAttribute("error", "Non puoi iscrivere la tua squadra al torneo "+torneo.getNome());
			model.addAttribute("torneiAperteIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(true));
			model.addAttribute("torneiChiuseIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(false));
			return "torneo/selectTorneo.html";
		}
	}
	
	@PostMapping(value = "/manager/teams/add")
	public String postAddSquadra(@RequestParam("logo-image")MultipartFile logo, @RequestParam("idTorneo")Long idTorneo,@Valid @ModelAttribute Squadra squadra, BindingResult bindingResult, Model model) throws IOException {
		Torneo torneo = this.torneoService.findTorneoByIdTorneo(idTorneo);
		squadra.setTorneo(torneo);
		this.squadraValidator.validate(squadra, bindingResult);
		if(bindingResult.hasErrors()) {

			model.addAttribute("torneo", torneo);
			model.addAttribute("squadra", squadra);
			return "squadra/formAddSquadra.html";
		}
		
    	if (!logo.isEmpty()) { // Verifica se il file è vuoto

            Path fileNameAndPath = Paths.get("src/main/resources/static/images/squadre/loghi", logo.getOriginalFilename());
            Files.write(fileNameAndPath, logo.getBytes());
            squadra.setLogo("/images/squadre/loghi/" + logo.getOriginalFilename());
        }
		
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		GestoreSquadra manager = null;

		if(principal instanceof UserDetails) {
			
			UserDetails user = (UserDetails) principal;
		    Credenziali credenziali = this.credenzialiService.findCredenzialiByUsername(user.getUsername());
		    manager = this.gestoreSquadraService.findGestoreSquadraByCredenziali(credenziali);
		}
		else {
			
			DefaultOAuth2User user = (DefaultOAuth2User) principal;
		    String username = user.getAttribute("email");
		    Credenziali credenziali = this.credenzialiService.findCredenzialiByUsername(username);
		    manager = this.gestoreSquadraService.findGestoreSquadraByCredenziali(credenziali);
		}
    	
    	squadra.setTeamManager(manager);
		this.squadraService.save(squadra); //forse non qua
		return "redirect:/manager/teams/edit/"+squadra.getIdSquadra();
	}
	
	@GetMapping(value = "/manager/teams/edit/{idSquadra}")
	public String getFormAddGiocatoriSquadra(@PathVariable("idSquadra") Long idSquadra, Model model) {
		model.addAttribute("squadra", this.squadraService.findSquadraByIdSquadra(idSquadra));
		model.addAttribute("giocatori", this.giocatoreService.findGiocatoriBySquadraIdSquadra(idSquadra));
		model.addAttribute(new Giocatore());
		return "squadra/formManageSquadra.html";
	}
	
	@GetMapping(value = "/manager/teams/remove/{idSquadra}")
	public String getRemoveSquadra(@PathVariable("idSquadra")Long idSquadra) throws IOException {
		Squadra squadra = this.squadraService.findSquadraByIdSquadra(idSquadra);
		this.squadraService.remove(squadra);
        Path fileNameAndPath = Paths.get("src/main/resources/static/"+squadra.getLogo());
        Files.delete(fileNameAndPath);
		return "redirect:/manager/dashboard";
	}
}
