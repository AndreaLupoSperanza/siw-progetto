package it.uniroma3.siw.demospring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.uniroma3.siw.demospring.services.FotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.demospring.model.Dipendente;
import it.uniroma3.siw.demospring.model.Foto;


@Controller
public class GalleriaController {

	@Autowired
	private FotoService fotoService;

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
					this.fotoService.aggiornaCarrelloConFotoSelezionateAdesso(listFotoIds, model, session);
					return "index";
				}else {
					if(action.equals("carrello")) {
						List<Foto> fotoSelezionate = (List<Foto>) session.getAttribute("fotoSelezionatePrima");
						model.addAttribute("fotoVisualizzate", fotoSelezionate);
						return "dettagliOrdinazione";
					}else {
						//provo ad aggiungere il tasto home
						if(action.equals("home")) {
							model.addAttribute("pagPrec", 0);
							model.addAttribute("pagSucc", 2);
							model.addAttribute("fotoVisualizzate", this.fotoService.getFotoPaginaSuccessivaDaVisualizzare(1));
							return"index";
						}
						//fine
						int pagina=1;
						System.out.println(request.getParameter(action));
						//Action qui è il valore della pagina in cui voglio andare
						pagina = Integer.parseInt(action);
						if(pagina<1)
							pagina=1;
						System.out.println(pagina);
						List<Foto> successiveFotoDaVisualizzare = this.fotoService.getFotoPaginaSuccessivaDaVisualizzare(pagina);
						model.addAttribute("fotoVisualizzate", successiveFotoDaVisualizzare);
						model.addAttribute("pagPrec", pagina-1);
						model.addAttribute("pagSucc", pagina+1);
					}
				}

		return prossimaPagina;
	}



	/*
	 * Serve per ottenere tutte le foto dell'ordine attuale
	 */
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
			return "foto.html";
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


}
