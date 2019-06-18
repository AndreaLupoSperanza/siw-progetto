package it.uniroma3.siw.demospring.controller;



import java.util.List;

import javax.servlet.http.HttpSession;
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

import it.uniroma3.siw.demospring.services.AlbumService;
import it.uniroma3.siw.demospring.services.AlbumValidator;
import it.uniroma3.siw.demospring.services.AutoreService;
import it.uniroma3.siw.demospring.services.AutoreValidator;
import it.uniroma3.siw.demospring.services.FotoService;
import it.uniroma3.siw.demospring.services.FotoValidator;
import it.uniroma3.siw.demospring.services.OrdineService;

@Controller
public class AdminController {

	@Autowired
	private OrdineService ordineService;
	@Autowired
	private AlbumValidator albumValidator;
	@Autowired
	private AutoreService autoreService;
	@Autowired
	private AutoreValidator autoreValidator;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private FotoService fotoService;
	@Autowired
	private FotoValidator fotoValidator;

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
		return "amministratore";
	}

	@RequestMapping(value = "/ordini", method = RequestMethod.GET)
	public String getTuttiOrdini(Model model) {
		model.addAttribute("ordini", this.ordineService.tuttiOrdini());
		return "amministratore";
	}

	@RequestMapping(value = "/vaiAddAutore")
	public String vaiAddAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "addAutore";
	}

	@RequestMapping(value = "/vaiAddAlbum")
	public String vaiAddAlbum(Model model) {
		model.addAttribute("autori", this.autoreService.tuttiGliAutore());
		return "selAutorePerAddAlbum";
	}
	
	@RequestMapping(value = "/selAutorePerAddAlbum/{id}", method = RequestMethod.GET)
	public String selAutorePerAddAlbum(
			@PathVariable("id") Long id,
			HttpSession session,
			Model model) {
		Autore autoreSel = this.autoreService.getSingoloAutore(id);
		session.setAttribute("idAutoreAlbum", autoreSel);
		model.addAttribute("album", new Album());
		return "addAlbum.html";
	}

	@RequestMapping(value = "/vaiAddFoto")
	public String vaiAddFoto(Model model) {
		model.addAttribute("gliAlbum", this.albumService.tuttiGliAlbum());
		return "selAlbumPerAddFoto";
	}
	
	
	@RequestMapping(value = "/selAlbumPerAddFoto/{id}", method = RequestMethod.GET)
	public String selAlbumPerAddFoto(
			@PathVariable("id") Long id,
			HttpSession session,
			Model model) {
		Album albumSel = this.albumService.getSingoloAlbum(id);
		session.setAttribute("idAlbumFoto", albumSel);
		model.addAttribute("foto", new Foto());
		return "addFoto.html";
	}
	

	@RequestMapping(value = "/addFoto", method = RequestMethod.POST)
	public String addAlbum(@Valid @ModelAttribute("foto") Foto foto,
			Model model, 
			BindingResult bindingResult,
			HttpSession session) {

		Album albumSel = (Album) session.getAttribute("idAlbumFoto");
		foto.setAlbum(albumSel);
		this.fotoValidator.validate(foto, bindingResult);
		if(!bindingResult.hasErrors()) {
			if(this.fotoService.esiste(foto)) {
				model.addAttribute("esiste", "Una foto con questo nome esiste già");
			}else {
				this.fotoService.inserisci(foto);
				model.addAttribute("leFoto", this.fotoService.tutteFoto());
				return "tutteLeFoto.html";
			}
		}
		model.addAttribute("foto", foto);
		return "addFoto.html";
	}

	@RequestMapping(value = "/addAutore", method = RequestMethod.POST)
	public String addAutore(@Valid @ModelAttribute("autore") Autore autore,
			Model model, BindingResult bindingResult) {

		this.autoreValidator.validate(autore, bindingResult);
		if(!bindingResult.hasErrors()) {
			if(this.autoreService.esiste(autore)) {
				model.addAttribute("esiste", "Questo autore esiste già");
			}else {
				this.autoreService.inserisci(autore);
				model.addAttribute("autore", this.autoreService.tuttiGliAutore());
				return "autori";
			}
		}
		model.addAttribute("autore", autore);
		return "addAutore";
	}
	
	
	
	
	@RequestMapping(value = "/addFotoLink", method = RequestMethod.POST)
	public String addFoto(@Valid @ModelAttribute("foto") Foto foto,
			Model model, BindingResult bindingResult) {

		//this.fotoValidator.validate(foto, bindingResult);
		if(!bindingResult.hasErrors()) {
			if(this.fotoService.esiste(foto)) {
				model.addAttribute("esiste", "Una foto con questo link esiste già");
			}else {
				
				this.fotoService.inserisci(foto);
				model.addAttribute("foto", this.fotoService.tutteFoto());
				return "tutteFoto";
			}
		}
		model.addAttribute("foto", foto);
		return "fotoForm";
	}





}
