package it.uniroma3.siw.demospring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.demospring.model.Foto;
import it.uniroma3.siw.demospring.repository.FotoRepository;


@Controller
public class GalleriaController {
	
	 @Autowired
	 private FotoRepository fotoRepository;
	
	@RequestMapping("/index")
	public String paginaInizio(Model model) {
		List<Foto> foto = (List<Foto>) this.fotoRepository.findAll();
		model.addAttribute("fotoVisualizzate", foto);
		return "index";
	}
	
}
