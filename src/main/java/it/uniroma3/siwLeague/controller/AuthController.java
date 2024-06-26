package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwLeague.controller.validator.GestoreSquadraValidator;
import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.service.GestoreSquadraService;
import jakarta.validation.Valid;

@Controller
public class AuthController {
	
	@Autowired
	private GestoreSquadraService gestoreSquadraService;
	
	@Autowired
	private GestoreSquadraValidator gestoreSquadraValidator;
	
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
	public String postRegisterPage(@Valid @ModelAttribute GestoreSquadra manager, Model model, BindingResult bindingResult) {
		
		this.gestoreSquadraValidator.validate(manager, bindingResult);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("gestoreSquadra", manager);
			return "register.html";
		}
		
		//password cifrata
		manager.getCredenziali().setRuolo("DEFAULT");
		manager.getCredenziali().setPassword(passwordEncoder.encode(manager.getCredenziali().getPassword()));
		gestoreSquadraService.save(manager);
		return "redirect:/";
	}
}
