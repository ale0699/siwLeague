package it.uniroma3.siwLeague.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwLeague.controller.validator.GestoreSquadraValidator;
import it.uniroma3.siwLeague.model.Credenziali;
import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.service.CredenzialiService;
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
	
	@Autowired
	private CredenzialiService credenzialiService;
	
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
	
	
	@GetMapping(value = "/success")
	public String getSuccess(Authentication authentication) {
		
		// google
		if (authentication.getPrincipal() instanceof DefaultOidcUser) {
		    
		    DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
		    String username = user.getAttribute("email");
		    Credenziali credenziali = this.credenzialiService.findCredenzialiByUsername(username);
		    
		    //aggiorno autorità
	        Collection<GrantedAuthority> newAuthorities = new ArrayList<>(authentication.getAuthorities());
	        newAuthorities.add(new SimpleGrantedAuthority("DEFAULT"));
	        DefaultOAuth2User newUser = new DefaultOAuth2User(newAuthorities, user.getAttributes(), "email");
	        OAuth2AuthenticationToken newAuth = new OAuth2AuthenticationToken(newUser, newAuthorities, "google");
	        SecurityContextHolder.getContext().setAuthentication(newAuth);
		    
		    
		    if (credenziali==null) {
		        
		    	GestoreSquadra manager = new GestoreSquadra();
		    	manager.setNome(user.getAttribute("given_name"));
		    	manager.setCognome(user.getAttribute("family_name"));
		    	Credenziali newManagerCredenziali = new Credenziali();
		    	newManagerCredenziali.setUsername(username);
		    	newManagerCredenziali.setPassword("fittizia");
		    	newManagerCredenziali.setRuolo("DEFAULT");
		    	manager.setCredenziali(newManagerCredenziali);
		    	
		    	this.gestoreSquadraService.save(manager);	
		    }
		}

		return "redirect:/dashboard";
	}
}
