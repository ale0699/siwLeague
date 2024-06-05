package it.uniroma3.siwLeague.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping(value = "/")
	public String getIndexPage() {
		return "index.html";
	}
}
