package it.uniroma3.siw.demospring.services;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.demospring.model.Dipendente;

public class DipendenteValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return Dipendente.class.equals(arg0);
	}

	@Override
	public void validate(Object o, Errors error) {
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "email", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "password", "required");
	}

}
