package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.service.CredenzialiService;
import it.uniroma3.siwLeague.service.GestoreSquadraService;
import it.uniroma3.siwLeague.service.SquadraService;
import it.uniroma3.siwLeague.service.TorneoService;

@Controller
public class IndexController {
	
	@Autowired
	private SquadraService squadreService;
	
	@Autowired
	private CredenzialiService credenzialiService;
	
	@Autowired
	private GestoreSquadraService gestoreSquadraService;
	
	@Autowired
	private TorneoService torneoService;
	
	@GetMapping(value = "/")
	public String getIndexPage(Model model) {
		model.addAttribute("squadre", this.squadreService.findAllSquadre());
		return "index.html";
	}
	
	@GetMapping(value = "/manager/dashboard")
	public String getDashboardPage(Model model) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		GestoreSquadra manager = this.gestoreSquadraService.findGestoreSquadraByCredenziali(this.credenzialiService.findCredenzialiByUsername(user.getUsername()));
		model.addAttribute("squadre", this.squadreService.findSquadreIdGestoreSquadra(manager.getIdGestoreSquadra()));
		return "dashboard.html";
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
}
