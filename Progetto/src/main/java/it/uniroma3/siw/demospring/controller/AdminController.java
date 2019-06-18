package it.uniroma3.siw.demospring.controller;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Autore;
import it.uniroma3.siw.demospring.model.Foto;
import it.uniroma3.siw.demospring.model.Ordine;
import it.uniroma3.siw.demospring.model.RigaOrdinazione;
import it.uniroma3.siw.demospring.model.Studente;
import it.uniroma3.siw.demospring.repository.AutoreRepository;
import it.uniroma3.siw.demospring.services.AutoreService;
import it.uniroma3.siw.demospring.services.AutoreValidator;
import it.uniroma3.siw.demospring.services.OrdineService;

@Controller
public class AdminController {
	
	@Autowired
	private OrdineService ordineService;
	@Autowired
	private AutoreValidator autoreValidator;
	@Autowired
	private AutoreService autoreService;

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
	
	@RequestMapping(value = "/vaiAddAutore")
	public String vaiAddAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "addAutore";
	}
	
	@RequestMapping(value = "/vaiAddAlbum")
	public String vaiAddAlbum(Model model) {
		model.addAttribute("album", new Album());
		return "addAlbum";
	}
	
	@RequestMapping(value = "/vaiAddFoto")
	public String vaiAddFoto(Model model) {
		model.addAttribute("foto", new Foto());
		return "addFoto";
	}
	
	@RequestMapping(value = "/addAutore", method = RequestMethod.POST)
	public String addAutore(@Valid @ModelAttribute("autore") Autore autore,
			Model model, BindingResult bindingResult) {
		
		this.autoreValidator.validate(autore, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.autoreService.inserisci(autore);
			model.addAttribute("autore", this.autoreService.tutti());
			return "autori";
		}else {
			return "autoreForm.html";
		}
	}
	
	
	
}
