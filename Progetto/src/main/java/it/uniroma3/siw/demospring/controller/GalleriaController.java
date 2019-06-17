package it.uniroma3.siw.demospring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.uniroma3.siw.demospring.services.FotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		model.addAttribute("fotoVisualizzate", this.fotoService.findAllFoto());
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
			HttpServletRequest request
			) {
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


}
