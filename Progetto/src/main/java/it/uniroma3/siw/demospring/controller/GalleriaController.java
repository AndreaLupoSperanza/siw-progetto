package it.uniroma3.siw.demospring.controller;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.siw.demospring.services.FotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.demospring.model.Foto;


@Controller
public class GalleriaController {
	
	@Autowired
	 private FotoService fotoService;
	
	@RequestMapping("/")
	public String paginaInizio(Model model) {
		model.addAttribute("fotoVisualizzate", this.fotoService.tutte());
		return "index";
	}
	
//	@RequestMapping(value = "/getFotoSelezionate")
//	public String getFotoSelezionate(@RequestParam(value = "addFotoSelezionate") String[] listFotoIds,
//									 Model model) {
//		List<Foto> listFoto = new ArrayList<>();
//
//
//		List<Long> fotoIds = new ArrayList<>();
//
//		if (listFotoIds == null || listFotoIds.length == 0) {
	
//	@RequestMapping(value = "/addFotoSelezionate", method = RequestMethod.POST)
//	public String prendiTutteLeFotoSelezionate(@RequestParam(value = "listaFoto") String[]  idFotoLista, 
//			Model model) {
//		List<Foto> fotoLista = new ArrayList<Foto>();
//		System.out.println("lunghezza idFotoLista: "+idFotoLista.length);
//		try {
//			for(String idFotoSingola : idFotoLista) {
//				Long id = Long.parseLong(idFotoSingola.trim());
//				fotoLista.add(this.fotoService.fotoPerId(id));
//				System.out.println("id Foto sel: "+idFotoSingola);
//			}
//		}catch (NullPointerException e) {
//			System.out.println("Foto richieste VUOTE");
//			model.addAttribute("fotoVisualizzate", listFoto);
//		} else {
//			for(String idStringFoto : listFotoIds) {
//				Long idFoto = Long.parseLong(idStringFoto.trim());
//				System.out.println("id Foto sel: " + idFoto);
//				fotoIds.add(idFoto);
//			}
//			listFoto = fotoService.findAllFoto(fotoIds);
//			model.addAttribute("fotoVisualizzate", listFoto);
//		}
//
//		return "index";
//	}
	
	@RequestMapping(value="/foto/{id}", method = RequestMethod.GET)
	public String getFoto(@PathVariable("id") Long id, Model model){
		if (id!=null) {
			model.addAttribute("foto",this.fotoService.fotoPerId(id));
			return "foto.html";
		}
		else {
			model.addAttribute("fotoVisualizzate", this.fotoService.tutte());
			return "index";
		}
	}
}
