package it.uniroma3.siwLeague.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwLeague.model.GestoreSquadra;
import it.uniroma3.siwLeague.service.CredenzialiService;

@Component
public class GestoreSquadraValidator implements Validator {

	@Autowired
	private CredenzialiService credenzialiService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return GestoreSquadra.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		GestoreSquadra teamManager = (GestoreSquadra) target;
		
		if(this.credenzialiService.existsCredenzialiByUsername(teamManager.getCredenziali().getUsername())) {
			
			errors.reject("message.UsernameEsistente");
		}
	}

}
