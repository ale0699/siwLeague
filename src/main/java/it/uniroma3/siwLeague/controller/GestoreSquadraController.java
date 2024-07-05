package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwLeague.model.Credenziali;
import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.service.CredenzialiService;
import it.uniroma3.siwLeague.service.GestoreSquadraService;
import it.uniroma3.siwLeague.service.SquadraService;

@Controller
public class GestoreSquadraController {
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private CredenzialiService credenzialiService;
	
	@Autowired
	private GestoreSquadraService gestoreSquadraService;
	
	@GetMapping(value = "/manager/edit")
	public String getFormEditProfile(Model model) {
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
		
		model.addAttribute("gestoreSquadra", manager);
		return "manager/formEditProfile.html";
	}
	
	@PostMapping(value = "/manager/update")
	public String postUpdateProfile(@ModelAttribute GestoreSquadra managerEdited) {
		GestoreSquadra manager = this.gestoreSquadraService.findGestoreSquadraById(managerEdited.getIdGestoreSquadra());
		manager.setNome(managerEdited.getNome());
		manager.setCognome(managerEdited.getCognome());
		this.gestoreSquadraService.save(manager);
		return "redirect:/manager/dashboard";
	}
	
	@GetMapping(value = "/manager/dashboard")
	public String getDashboard(Model model) {
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
		
	    model.addAttribute("manager", manager);
	    model.addAttribute("squadre", this.squadraService.findSquadreIdGestoreSquadra(manager.getIdGestoreSquadra()));
		return "manager/dashboard.html";
	}
}
