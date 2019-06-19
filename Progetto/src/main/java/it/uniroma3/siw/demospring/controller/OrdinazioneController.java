package it.uniroma3.siw.demospring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.demospring.model.Foto;
import it.uniroma3.siw.demospring.model.Ordine;
import it.uniroma3.siw.demospring.services.FotoService;
import it.uniroma3.siw.demospring.services.OrdineFormValidator;

@Controller
public class OrdinazioneController {
	@Autowired
	private OrdineFormValidator ordineFormValidator;
	@Autowired
	private FotoService fotoService;



	@RequestMapping(value = "/cancellaOppureAcquistaSelezionate", method = RequestMethod.GET)
	public String eliminaFotoSelezionate(
			@RequestParam (value = "action") String action,
			Model model,
			HttpSession session,
			HttpServletRequest request) {
		System.out.println(action);
		if(action.equals("eliminaSel")) {
			String[] listFotoIds = request.getParameterValues("fotoSelezione");
			this.fotoService.eliminaFotoSelezionateAdesso(listFotoIds, model, session);
			model.addAttribute("fotoVisualizzate", session.getAttribute("fotoSelezionatePrima"));
		}else {
			if(action.equals("acquista")) {
				List<Foto> fotoSelezionatePrima = (List<Foto>) session.getAttribute("fotoSelezionatePrima");
				if(fotoSelezionatePrima!=null) {
					if(fotoSelezionatePrima.size()>0) {
						model.addAttribute("ordine", new Ordine());
						return "ordineForm";
					}else {
						model.addAttribute("nessunaFotoSelezionata","Nessuna foto selezionata");
					}
				}


			}
		}
		return "dettagliOrdinazione";
	}

	@RequestMapping(value = "/confermaOppureAnnullaAcquisto", method = RequestMethod.POST)
	public String confermaOppureAnnullaAcquisto(
			@ModelAttribute (value = "ordine") Ordine ordine,
			@RequestParam (value = "action") String action,
			HttpSession session,
			HttpServletRequest request,
			BindingResult bindingResult,
			Model model) {
		if(action.equals("annulla")) {
			session.invalidate();
			return "peccatoNonTiAbbiamoFregato";
		}
		this.ordineFormValidator.validate(ordine, bindingResult);
		if(!bindingResult.hasErrors()) {
			System.out.println(action);
			if(action.equals("conferma")) {
				this.fotoService.salvaMolteFoto(ordine, session, request);
				session.invalidate();
				return "graziePerIlTuoAcquisto";
			}
		}
		model.addAttribute("ordine", ordine);
		return "ordineForm";
	}
}

