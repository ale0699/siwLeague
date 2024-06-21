package it.uniroma3.siwLeague.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwLeague.model.Giocatore;
import it.uniroma3.siwLeague.model.Partita;
import it.uniroma3.siwLeague.model.Squadra;
import it.uniroma3.siwLeague.model.Torneo;
import it.uniroma3.siwLeague.service.PartitaService;
import it.uniroma3.siwLeague.service.SquadraService;
import it.uniroma3.siwLeague.service.TorneoService;

@Controller
public class TorneoController {

	@Autowired
	private TorneoService torneoService;

	@Autowired
	private SquadraService squadraService;

	@Autowired
	private PartitaService partitaService;

	@GetMapping(value = "/tornei")
	public String getAllTornei(Model model) {
		model.addAttribute("tornei", this.torneoService.findAllTornei());
		return "torneo/tornei.html";
	}

	@GetMapping(value = "/torneo/{idTorneo}")
	public String getTorneo(@PathVariable("idTorneo") Long idTorneo, Model model) {
		List<Partita> tuttePartiteTorneo = this.partitaService.findAllPartiteByIdTorneo(idTorneo);
		List<Squadra> squadrePartecipantiTorneo = this.squadraService.findSquadrePartecipantiTorneoByIdTorneo(idTorneo);
		Torneo torneo = this.torneoService.getTorneoByIdTorneo(idTorneo);

		model.addAttribute("partite", tuttePartiteTorneo);
		model.addAttribute("squadrePartecipanti", squadrePartecipantiTorneo);
		model.addAttribute("torneo", torneo);

		// Aggiorno la persistenza ogni volta che visualizzo un torneo
		for (Squadra squadra : squadrePartecipantiTorneo) {
			squadra.setPunti();
			this.squadraService.save(squadra);
		}

		// Crea una lista di marcatori del torneo
		Map<Giocatore, Integer> marcatoriTorneo = new HashMap<>();
		
		for (Partita partitaCorrente : tuttePartiteTorneo) {

			Map<Integer, Giocatore> marcatoriPartitaCorrente = partitaCorrente.getMarcatori();
			
			//itero su tutti i minuti in cui si è segnato un gol
			for (Integer minuto : marcatoriPartitaCorrente.keySet()) {
				
				//è il giocatore che ha segnato al minuto (Integer minuto)
				Giocatore giocatoreMarcatoreNellaPartitaCorrente = marcatoriPartitaCorrente.get(minuto);
				
				//se è presente già nella mappa dei marcatori del torneo aggiungo un gol in più 
				if (marcatoriTorneo.containsKey(giocatoreMarcatoreNellaPartitaCorrente)) {

					marcatoriTorneo.put(giocatoreMarcatoreNellaPartitaCorrente, (marcatoriTorneo.get(giocatoreMarcatoreNellaPartitaCorrente) + 1));
				}
				//se non è presente lo inizializzo con un gol
				else {

					marcatoriTorneo.put(giocatoreMarcatoreNellaPartitaCorrente, 1);
				}
			}
		}

		model.addAttribute("marcatori", marcatoriTorneo);

		return "torneo/torneo.html";
	}

	@GetMapping(value = "/selectTorneo")
	public String getSelectTorneo(Model model) {
		model.addAttribute("torneiAperteIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(true));
		model.addAttribute("torneiChiuseIscrizioni", this.torneoService.findTorneiByIscrizioneInCorso(false));
		return "torneo/selectTorneo.html";
	}
}
