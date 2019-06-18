package it.uniroma3.siw.demospring.services;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.demospring.model.Ordine;

@Component
public class OrdineFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return Ordine.class.equals(arg0);
	}

	@Override
	public void validate(Object o, Errors error) {
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "nomeAcquirente", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "cognomeAcquirente", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "numeroTelefonico", "required");
	}

}
