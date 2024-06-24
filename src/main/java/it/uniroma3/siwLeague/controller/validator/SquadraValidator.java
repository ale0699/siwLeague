package it.uniroma3.siwLeague.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwLeague.model.Squadra;
import it.uniroma3.siwLeague.service.SquadraService;

@Component
public class SquadraValidator implements Validator {
	
	@Autowired
	private SquadraService squadraService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Squadra.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Squadra squadra = (Squadra) target;
		
		if(this.squadraService.existsSquadraByNomeAndTorneo(squadra.getNome(), squadra.getTorneo().getIdTorneo())) {
			
			errors.reject("message.squadraGiaEsistenteNelTorneo");
		}
	}	
}
