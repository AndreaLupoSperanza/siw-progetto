package it.uniroma3.siw.demospring.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	

	@RequestMapping(value = "/addFotoSelezionate", method = RequestMethod.POST)
	public String prendiTutteLeFotoSelezionate(@RequestParam(value = "listaFoto") String[]  idFotoLista, 
			Model model) {
		List<Foto> fotoLista = new ArrayList<Foto>();
		System.out.println("lunghezza idFotoLista: "+idFotoLista.length);
		try {
			for(String idFotoSingola : idFotoLista) {
				Long id = Long.parseLong(idFotoSingola.trim());
				fotoLista.add(this.fotoRepository.findById(id).get());
				System.out.println("id Foto sel: "+idFotoSingola);
			}
		}catch (NullPointerException e) {
			System.out.println("Foto richieste VUOTE");
		}

		model.addAttribute("fotoVisualizzate", fotoLista);
		return "index";
	}
	
}
