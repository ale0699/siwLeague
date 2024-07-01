package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.service.CredenzialiService;
import it.uniroma3.siwLeague.service.GestoreSquadraService;
import it.uniroma3.siwLeague.service.SquadraService;

@Controller
public class GestoreSquadraController {
	
	@Autowired
	private SquadraService squadreService;
	
	@Autowired
	private CredenzialiService credenzialiService;
	
	@Autowired
	private GestoreSquadraService gestoreSquadraService;
	
	@GetMapping(value = "/manager/edit")
	public String getFormEditProfile(Model model) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		GestoreSquadra manager = this.gestoreSquadraService.findGestoreSquadraByCredenziali(this.credenzialiService.findCredenzialiByUsername(user.getUsername()));
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
	public String getDashboardPage(Model model) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		GestoreSquadra manager = this.gestoreSquadraService.findGestoreSquadraByCredenziali(this.credenzialiService.findCredenzialiByUsername(user.getUsername()));
		model.addAttribute("manager", manager);
		model.addAttribute("squadre", this.squadreService.findSquadreIdGestoreSquadra(manager.getIdGestoreSquadra()));
		return "manager/dashboard.html";
	}
}
