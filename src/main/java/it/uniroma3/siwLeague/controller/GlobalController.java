package it.uniroma3.siwLeague.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {
	
	
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
	
}
