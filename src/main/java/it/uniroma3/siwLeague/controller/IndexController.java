package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siwLeague.service.SquadraService;

@Controller
public class IndexController {
	
	@Autowired
	private SquadraService squadreService;
	
//	@Autowired
//	private CredenzialiService credenzialiService;
//	
//	@Autowired
//	private GestoreSquadraService gestoreSquadraService;
	
	@GetMapping(value = "/")
	public String getIndexPage(Model model) {
		model.addAttribute("squadre", this.squadreService.findAllSquadre());
		return "index.html";
	}
	
	@GetMapping(value = "/dashboard")
	public String getDashboardPage(Model model) {
		//va cambiato
//		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		GestoreSquadra manager = this.gestoreSquadraService.findGestoreSquadraByCredenziali(this.credenzialiService.findCredenzialiByUsername(user.getUsername()));
		model.addAttribute("squadre", this.squadreService.findSquadreIdGestoreSquadra((long) 3));
		return "dashboard.html";
	}
}
