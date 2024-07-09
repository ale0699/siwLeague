package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siwLeague.model.Credenziali;
import it.uniroma3.siwLeague.service.CredenzialiService;

@ControllerAdvice
public class GlobalController {
	
	@Autowired
	private CredenzialiService credenzialiService;
	
	@ModelAttribute("username")
	public String getUsername() {
		
		if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			
			return null;
		}
		else {
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(principal instanceof UserDetails) {
				
				UserDetails user = (UserDetails) principal;
				return user.getUsername();
			}
			else {
				
				DefaultOAuth2User user = (DefaultOAuth2User) principal;
			    String username = user.getAttribute("email");
			    return username;
			}
		}
	}	
	
	@ModelAttribute("userRole")
	public String getRuolo() {

	    if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
	        return null;
	    } else {
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        Credenziali credenziali = null;

	        if (principal instanceof UserDetails) {
	            UserDetails user = (UserDetails) principal;
	            credenziali = this.credenzialiService.findCredenzialiByUsername(user.getUsername());
	        } else if (principal instanceof DefaultOAuth2User) {
	            DefaultOAuth2User user = (DefaultOAuth2User) principal;
	            credenziali = this.credenzialiService.findCredenzialiByUsername(user.getAttribute("email"));
	        }

	        if (credenziali != null) {
	            return credenziali.getRuolo().toLowerCase();
	        } else {

	            return null;
	        }
	    }
	}
	
}
