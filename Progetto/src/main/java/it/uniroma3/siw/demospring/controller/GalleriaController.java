package it.uniroma3.siw.demospring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.uniroma3.siw.demospring.services.AlbumService;
import it.uniroma3.siw.demospring.services.AlbumValidator;
import it.uniroma3.siw.demospring.services.AutoreService;
import it.uniroma3.siw.demospring.services.AutoreValidator;
import it.uniroma3.siw.demospring.services.FotoService;
import it.uniroma3.siw.demospring.services.FotoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Autore;
import it.uniroma3.siw.demospring.model.Dipendente;
import it.uniroma3.siw.demospring.model.Foto;


@Controller
public class GalleriaController {

	@Autowired
	private FotoValidator fotoValidator;
	@Autowired
	private FotoService fotoService;
	@Autowired
	private AutoreValidator autoreValidator;
	@Autowired
	private AutoreService autoreService;
	@Autowired
	private AlbumValidator albumValidator;
	@Autowired
	private AlbumService albumService;


	@RequestMapping("/")
	public String paginaInizio(Model model) {
		model.addAttribute("pagPrec", 0);
		model.addAttribute("pagSucc", 2);
		model.addAttribute("fotoVisualizzate", this.fotoService.getFotoPaginaSuccessivaDaVisualizzare(1));
		return "index";
	}


	/*
	 * prende le foto selezionate nella pagina, le salva nella sessione,
	 * ed ritorna le nuove foto da selezionare
	 */
	@RequestMapping(value = "/selezionaFoto", method = RequestMethod.GET)
	public String getFotoSelezionate(
			@RequestParam (value = "action") String action,
			Model model,
			HttpSession session,
			HttpServletRequest request) {
		String prossimaPagina = "index";
		System.out.println(action);
		if(action.equals("confermaSel")) {
			String[] listFotoIds = request.getParameterValues("fotoSelezione");
			int prossimaPag = Integer.parseInt((String) request.getParameter("prossimaPagina"));
			this.fotoService.aggiornaCarrelloConFotoSelezionateAdesso(listFotoIds, model, session);
			List<Foto> prossimeDaVisualizzare = this.fotoService.getFotoPaginaSuccessivaDaVisualizzare(prossimaPag);
			model.addAttribute("fotoVisualizzate", prossimeDaVisualizzare);
			model.addAttribute("pagPrec", prossimaPag-1);
			model.addAttribute("pagSucc", prossimaPag+1);
			return "index";
		}else {
			if(action.equals("carrello")) {
				List<Foto> fotoSelezionate = (List<Foto>) session.getAttribute("fotoSelezionatePrima");
				model.addAttribute("fotoVisualizzate", fotoSelezionate);
				return "dettagliOrdinazione";
			}else {
				//fine
				int pagina=1;
				System.out.println(request.getParameter(action));
				//Action qui è il valore della pagina in cui voglio andare
				pagina = Integer.parseInt(action);
				if(pagina<1)
					pagina=1;
				System.out.println(pagina);
				String[] listFotoIds = request.getParameterValues("fotoSelezione");
				this.fotoService.aggiornaCarrelloConFotoSelezionateAdesso(listFotoIds, model, session);
				List<Foto> successiveFotoDaVisualizzare = this.fotoService.getFotoPaginaSuccessivaDaVisualizzare(pagina);
				model.addAttribute("fotoVisualizzate", successiveFotoDaVisualizzare);
				model.addAttribute("pagPrec", pagina-1);
				model.addAttribute("pagSucc", pagina+1);
			}
		}

		return prossimaPagina;
	}

	@RequestMapping("/getDettagliOrdine")
	public String dettagliOrdinazione(Model model, 
			HttpSession session) {
		List<Foto> fotoSelezionate = (List<Foto>) session.getAttribute("fotoSelezionatePrima");
		model.addAttribute("fotoSelezionate", fotoSelezionate);
		return "dettagliOrdinazione";
	}

	@RequestMapping(value="/foto/{id}", method = RequestMethod.GET)
	public String getFoto(@PathVariable("id") Long id, Model model){
		if (id!=null) {
			model.addAttribute("foto",this.fotoService.findFotoById(id));
			return "fotoAdmin.html";
		}
		else {
			model.addAttribute("fotoVisualizzate", this.fotoService.findAllFoto());
			return "index";
		}
	}

	@RequestMapping("/login")
	public String login(Model model) {
		Dipendente dipendente = new Dipendente();
		model.addAttribute("dipendente", dipendente);
		return "login";
	}
	@RequestMapping( value = "/vaiCercaTuttiAlbumDiUnAutore", method = RequestMethod.GET)
	public String cercaTuttiAlbumDiUnAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "cercaTuttiAlbumDiAutore.html";
	}

	@RequestMapping(value = "/cercaAlbumAutore", method = RequestMethod.POST)
	public String cercaAlbumAutore(@ModelAttribute("autore") Autore autore,
			Model model,
			BindingResult bindingResult) { 
		this.autoreValidator.validate(autore, bindingResult);
		if(!bindingResult.hasErrors()) {
			List<Autore> autPerNome = this.autoreService.findByNome(autore.getNome());
			List<Autore> autPerCognome = this.autoreService.findByCognome(autore.getCognome());
			boolean trovato=false;
			for(Autore aut : autPerNome) {
				if(autPerCognome.contains(aut)) {
					model.addAttribute("autore",aut);
					model.addAttribute("gliAlbum",aut.getAlbum());
					trovato=true;
				}
			}
			if(trovato) {
				return "tuttiAlbumDiUnAutore.html";
			}else {
				model.addAttribute("autore",autore);
				model.addAttribute("nonEsiste", "Questo autore non è presente nel sistema");
				return "cercaTuttiAlbumDiAutore";
			}

		}
		model.addAttribute("autore",autore);
		return "cercaTuttiAlbumDiAutore";
	}

	@RequestMapping( value = "/vaiCercaTutteFotoDiUnAlbum", method = RequestMethod.GET)
	public String cercaTutteFotoDiUnAlbum(Model model) {
		model.addAttribute("album", new Album());
		return "cercaTutteFotoDiUnAlbum.html";
	}

	@RequestMapping(value = "/cercaFotoDiUnAlbum", method = RequestMethod.POST)
	public String cercaAlbumAutore(@ModelAttribute("album") Album album,
			Model model,
			BindingResult bindingResult) { 
		this.albumValidator.validate(album, bindingResult);
		if(!bindingResult.hasErrors()) {
			Album alb = this.albumService.findAlbumByNome(album.getNome().trim());
			if(alb!=null) {
				model.addAttribute("autore", alb.getAutore());
				model.addAttribute("leFoto", alb.getFotografie());
				return "tutteLeFotoDiUnAlbum.html";
			}else {
				model.addAttribute("nonEsiste", "Questo album non esiste");
				return "cercaTutteFotoDiUnAlbum.html";
			}
		}
		return "cercaTutteFotoDiUnAlbum.html";
	}

	@RequestMapping(value="/fotoUtente/{id}", method = RequestMethod.GET)
	public String getFotoUtente(@PathVariable("id") Long id, Model model){
		if (id!=null) {
			model.addAttribute("foto",this.fotoService.findFotoById(id));
			return "fotoUtente.html";
		}
		else {
			return "index";
		}
	}

	@RequestMapping( value = "/vaiCercaFotoPerNome", method = RequestMethod.GET)
	public String cercaFotoPerNome(Model model) {
		model.addAttribute("foto", new Foto());
		return "cercaSingolaFoto.html";
	}

	@RequestMapping(value = "/cercaFoto", method = RequestMethod.POST)
	public String singolaFoto(@ModelAttribute("album") Foto foto,
			Model model,
			BindingResult bindingResult) { 
		this.fotoValidator.validate(foto, bindingResult);
		if(!bindingResult.hasErrors()) {
			Foto fot = this.fotoService.findFotoByNome(foto.getNome());
			if(fot!=null) {
				model.addAttribute("foto", fot);
				return "fotoUtente";
			}else {
				model.addAttribute("foto", foto);
				model.addAttribute("nonEsiste", "Questa foto non esiste");
				return "cercaSingolaFoto.html";
			}
		}
		model.addAttribute("foto", foto);
		return "cercaSingolaFoto.html";
	}	
}



