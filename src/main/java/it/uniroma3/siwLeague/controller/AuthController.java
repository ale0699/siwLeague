package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.service.GestoreSquadraService;

@Controller
public class AuthController {
	
	@Autowired
	private GestoreSquadraService gestoreSquadraService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping(value = "/login")
	public String getLoginPage() {
		return "login.html";
	}
	
	@GetMapping(value = "/login/error")
	public String getLoginErrorPage(Model model) {
		model.addAttribute("error", "Username o password errati");
		return "login.html";
	}
	
	@GetMapping(value = "/register")
	public String getRegisterPage(Model model) {
		model.addAttribute(new GestoreSquadra());
		return "register.html";
	}
	
	@PostMapping(value = "/register")
	public String postRegisterPage(@ModelAttribute GestoreSquadra manager, Model model) {
		
		//password cifrata
		manager.getCredenziali().setRuolo("DEFAULT");
		manager.getCredenziali().setPassword(passwordEncoder.encode(manager.getCredenziali().getPassword()));
		gestoreSquadraService.save(manager);
		return "redirect:/";
	}
}
