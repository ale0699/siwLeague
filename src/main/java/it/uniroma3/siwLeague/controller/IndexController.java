package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siwLeague.model.Credenziali;
import it.uniroma3.siwLeague.service.CredenzialiService;
import it.uniroma3.siwLeague.service.SquadraService;
import it.uniroma3.siwLeague.service.TorneoService;

@Controller
public class IndexController {
	
	@Autowired
	private SquadraService squadreService;
	
	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private CredenzialiService credenzialiService;
	
	@GetMapping(value = "/")
	public String getIndexPage(Model model) {
		model.addAttribute("squadre", this.squadreService.findAllSquadre());
		model.addAttribute("tornei", this.torneoService.findTorneiByIscrizioneInCorso(false));
		return "index.html";
	}
	
	
	@GetMapping(value = "/dashboard")
	public String getDashboardPage(Model model) {
		
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) {
			
			UserDetails user = (UserDetails) principal;
			Credenziali credenziali = this.credenzialiService.findCredenzialiByUsername(user.getUsername());
			if(credenziali.getRuolo().equals("ADMIN")) {
				
				return "redirect:/admin/dashboard";
			}
			else {
				
				return "redirect:/manager/dashboard";
			}
		}
		else {
			
			DefaultOAuth2User user = (DefaultOAuth2User) principal;
		    String username = user.getAttribute("email");
			Credenziali credenziali = this.credenzialiService.findCredenzialiByUsername(username);
			if(credenziali.getRuolo().equals("ADMIN")) {
				
				return "redirect:/admin/dashboard";
			}
			else {
				
				return "redirect:/manager/dashboard";
			}
		}
		
	}
	
	@GetMapping(value = "/admin/dashboard")
	public String getAdminDashboardPage(Model model) {
		model.addAttribute("tornei", this.torneoService.findAllTornei());
		return "admin/dashboard.html";
	}
	
	@GetMapping(value = "/contacts")
	public String getContatti() {
		
		return "contatti.html";
	}
	
	@GetMapping(value = "/about")
	public String getAbout() {
		
		return "about.html";
	}
}
