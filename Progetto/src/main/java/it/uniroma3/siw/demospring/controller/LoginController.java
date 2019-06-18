package it.uniroma3.siw.demospring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.demospring.model.Dipendente;
import it.uniroma3.siw.demospring.repository.DipendenteRepository;
import it.uniroma3.siw.demospring.services.DipendenteValidator;
import it.uniroma3.siw.demospring.services.OrdineService;

@Controller
public class LoginController {
	
	@Autowired
	private DipendenteRepository dipendenteRepository;
	@Autowired
	private DipendenteValidator dipendenteValidator;
	@Autowired
	private OrdineService ordineService;

	@RequestMapping(value = "/loginControll", method = RequestMethod.POST)
	public String controllaCredenziali(
			@Valid @ModelAttribute("dipendente") Dipendente dipendente, 
			Model model, BindingResult bindingResult) {
		this.dipendenteValidator.validate(dipendente, bindingResult);
		if(!bindingResult.hasErrors()) {
			
			Dipendente dipendenteInMemoria = this.dipendenteRepository.findByEmail(dipendente.getEmail());
			if((dipendenteInMemoria!=null)&&(dipendente.checkPassword(dipendenteInMemoria))) {
				model.addAttribute("ordini", this.ordineService.tuttiOrdini());
				return "amministratore";
			}
		}
		model.addAttribute("dipendente", dipendente);
		//Hai inserito delle credenziali, ma sono sbagliate
		model.addAttribute("credenzialiErrate","Credenziali Errate");
		return "login";
				
		
		
		
	}
}
