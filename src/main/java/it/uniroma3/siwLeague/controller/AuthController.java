package it.uniroma3.siwLeague.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
	
	@GetMapping(value = "/login")
	public String getLoginPage() {
		
		return "login.html";
	}
}
