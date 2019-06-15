package it.uniroma3.siw.demospring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
		model.addAttribute("fotoVisualizzate", this.fotoService.findAllFoto());
		return "index";
	}


	/*
	 * prende le foto selezionate nella pagina, le salva nella sessione,
	 * ed ritorna le nuove foto da selezionare
	 */
	@RequestMapping(value = "/selezionaFoto", method = RequestMethod.POST)
	public String getFotoSelezionate(@RequestParam(value = "fotoSelezione") 
	String[] listFotoIds,
	Model model,
	HttpSession session) {
		String prossimaPagina = "index.html";
		/*
		 * controllo se ho delle foto in sessione
		 * se le ho le salvo
		 * se non le ho preparo la lista per accogliere le selezionateAdesso
		 */
		List<Foto> fotoSelezionatePrima = (List<Foto>) session.getAttribute("fotoSelezionatePrima");
		if(fotoSelezionatePrima==null) {
			fotoSelezionatePrima = new ArrayList<Foto>();
		}
		//System.out.println("lunghezza fotoId: "+listFotoIds.length);

		/*
		 * Preparo la lista che contiene gli id delle foto selezionate ora
		 */
		List<Long> fotoSelezionateAdessoIds = new ArrayList<>();
		/*
		 * Se non ho selezionato nulla non devo aggiungere nulla alla sessione
		 * ma preparo le nuove foto da visualizzare all apagina successiva
		 */
		if (listFotoIds == null || listFotoIds.length == 0) {
			System.out.println("Foto richieste VUOTE");
		}
		/*
		 * Altrimenti avrò selezionato delle foto 
		 * Vado a cercarle nel DB e mi prendo gli oggetti foto 
		 * E li aggiungo alla sessione preesistente
		 */
		else {
			//mi prendo parso tutti gli id da stringhe a Long
			//Tirandomi fuori una lista<Long>
			for(String idStringFoto : listFotoIds) {
				Long idFoto = Long.parseLong(idStringFoto.trim());
				System.out.println("id Foto sel: " + idFoto);
				fotoSelezionateAdessoIds.add(idFoto);
			}
			//Cerco le foto selezionate adesso
			List<Foto> fotoSelezionateAdesso = fotoService.findAllFoto(fotoSelezionateAdessoIds);
			//Le aggiungo alle selezionate prima
			fotoSelezionatePrima.addAll(fotoSelezionateAdesso);
			//Metto le foto di prima e adesso in sessione come nuove selezionatePrima
			session.setAttribute("fotoSelezionatePrima", fotoSelezionatePrima);
		}
		
		List<Foto> successiveFotoDaVisualizzare = this.fotoService.getSuccessiveFotoDaVisualizzare();
		model.addAttribute("fotoVisualizzate", successiveFotoDaVisualizzare);

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
