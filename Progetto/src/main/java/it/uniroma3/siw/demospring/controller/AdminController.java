package it.uniroma3.siw.demospring.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.demospring.model.Ordine;
import it.uniroma3.siw.demospring.model.RigaOrdinazione;
import it.uniroma3.siw.demospring.services.OrdineService;

@Controller
public class AdminController {
	
	@Autowired
	private OrdineService ordineService;

	@RequestMapping(value = "/ordine/{id}", method = RequestMethod.GET)
	public String getOrdine(@PathVariable ("id") Long id, Model model) {
		if(id!=null) {
			Ordine ordine = this.ordineService.ordinePerId(id);
			List<RigaOrdinazione> righeOrdinazione = ordine.getRigheOrdinazione();
			model.addAttribute("ordine", ordine);
			model.addAttribute("righeOrdinazione", righeOrdinazione);
			return "singoloOrdine";
		}
		model.addAttribute("ordini", this.ordineService.tuttiOrdini());
		return "elencoOrdini";
	}
	
	@RequestMapping(value = "/ordini", method = RequestMethod.GET)
	public String getTuttiOrdini(Model model) {
		model.addAttribute("ordini", this.ordineService.tuttiOrdini());
		return "elencoOrdini";
	}
	
	
	
}
