package it.uniroma3.siwLeague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siwLeague.service.TorneoService;

@Controller
public class TorneoController {
	
	@Autowired
	private TorneoService torneoService;
	
	@GetMapping(value = "/selectTorneo")
	public String getSelectTorneo(Model model) {
		model.addAttribute("torneiAperteIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(true));
		model.addAttribute("torneiChiuseIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(false));
		return "torneo/selectTorneo.html";
	}
}
