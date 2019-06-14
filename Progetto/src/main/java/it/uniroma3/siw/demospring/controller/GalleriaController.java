package it.uniroma3.siw.demospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.demospring.model.Foto;


@Controller
public class GalleriaController {
	
	@RequestMapping("/in")
	public String paginaInizio(Model model) {
		model.addAttribute("fotoVisualizzate", new Foto());
		return "index";
	}
	
}
