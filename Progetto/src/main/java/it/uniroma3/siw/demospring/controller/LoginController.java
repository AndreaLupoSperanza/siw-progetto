package it.uniroma3.siw.demospring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.demospring.model.Dipendente;
import it.uniroma3.siw.demospring.repository.DipendenteRepository;

@Controller
public class LoginController {
	
	@Autowired
	private DipendenteRepository dipendenteRepository;

	@RequestMapping(value = "/loginControll", method = RequestMethod.POST)
	public String controllaCredenziali(
			@ModelAttribute("dipendente") Dipendente dipendente, 
			Model model) {
		Dipendente dipendenteInMemoria = this.dipendenteRepository.findByEmail(dipendente.getEmail());
		if(dipendente.checkPassowrd(dipendenteInMemoria))
				return "amministratore";
		return "/login";
		
		
	}
}
