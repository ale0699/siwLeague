package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siwLeague.service.SquadraService;
import it.uniroma3.siwLeague.service.TorneoService;

@Controller
public class IndexController {
	
	@Autowired
	private SquadraService squadreService;
	
	@Autowired
	private TorneoService torneoService;
	
	@GetMapping(value = "/")
	public String getIndexPage(Model model) {
		model.addAttribute("squadre", this.squadreService.findAllSquadre());
		return "index.html";
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
