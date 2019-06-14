package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GalleriaController {
	
	@RequestMapping("/index")
	public String paginaInizio(Model model) {
		model.addAttribute("fotoVisualizzate", null);
		return "index";
	}
	
}
