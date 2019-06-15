package it.uniroma3.siw.demospring.controller;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.siw.demospring.services.FotoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.demospring.model.Foto;

@Controller
public class GalleriaController {

	private FotoService fotoService;

	public GalleriaController(FotoService fotoService) {
		this.fotoService = fotoService;
	}

	@RequestMapping("/index")
	public String index(Model model) {
		List<Foto> allFoto = fotoService.findAllFoto();
		model.addAttribute("fotoVisualizzate", allFoto);
		return "index";
	}
	

	@RequestMapping(value = "/getFotoSelezionate")
	public String getFotoSelezionate(@RequestParam(value = "addFotoSelezionate") String[] listFotoIds,
									 Model model) {
		List<Foto> listFoto = new ArrayList<>();
		System.out.println("lunghezza fotoId: "+listFotoIds.length);

		List<Long> fotoIds = new ArrayList<>();

		if (listFotoIds == null || listFotoIds.length == 0) {
			System.out.println("Foto richieste VUOTE");
			model.addAttribute("fotoVisualizzate", listFoto);
		} else {
			for(String idStringFoto : listFotoIds) {
				Long idFoto = Long.parseLong(idStringFoto.trim());
				System.out.println("id Foto sel: " + idFoto);
				fotoIds.add(idFoto);
			}
			listFoto = fotoService.findAllFoto(fotoIds);
			model.addAttribute("fotoVisualizzate", listFoto);
		}

		return "index";
	}
	
}
