package it.uniroma3.siw.demospring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import it.uniroma3.siw.demospring.services.FotoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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


	/*
	 * prende le foto selezionate nella pagina, le salva nella sessione,
	 * ed ritorna le nuove foto da selezionare
	 */
	@RequestMapping(value = "/getFotoSelezionate")
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
		if(fotoSelezionatePrima.isEmpty()) {
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
			List<Foto> successiveFotoDaVisualizzare = this.fotoService.getSuccessiveFotoDaVisualizzare();
			model.addAttribute("fotoVisualizzate", successiveFotoDaVisualizzare);
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
			model.addAttribute("fotoVisualizzate", fotoSelezionatePrima);
		}

		return prossimaPagina;
	}

}
