package it.uniroma3.siwLeague.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwLeague.model.Giocatore;
import it.uniroma3.siwLeague.model.Squadra;
import it.uniroma3.siwLeague.model.Torneo;
import it.uniroma3.siwLeague.service.GiocatoreService;

@Component
public class GiocatoreValidator implements Validator {
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Giocatore.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Giocatore giocatore = (Giocatore) target;
		Squadra squadra = giocatore.getSquadra();
		Torneo torneo = squadra.getTorneo();
		if(squadra.getGiocatori().size()==torneo.getNumeroMassimoGiocatoriIscrivibili()) {
			
			errors.reject("message.NumeroMassimoDiGiocatoriPerSquadra");
		}
		else {
			
			if(this.giocatoreService.existsGiocatoreByNumeroMagliaAndSquadra(giocatore.getNumeroMaglia(), squadra.getIdSquadra())) {
				
				errors.reject("message.NumeroDiMagliaGiàPresente");
			}
			if(this.giocatoreService.existsGiocatoreByNomeAndCognomeAndDataNascitaAndSquadra(giocatore.getNome(), giocatore.getCognome(), giocatore.getDataNascita(), squadra.getIdSquadra())) {
				
				errors.reject("message.GiocatoreGiaPresenteInSquadra");
			}
		}
	}
}
