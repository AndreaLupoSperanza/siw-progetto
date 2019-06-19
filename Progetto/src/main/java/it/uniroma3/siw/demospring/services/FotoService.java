package it.uniroma3.siw.demospring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.uniroma3.siw.demospring.model.Foto;
import it.uniroma3.siw.demospring.model.Ordine;
import it.uniroma3.siw.demospring.model.RigaOrdinazione;
import it.uniroma3.siw.demospring.repository.FotoRepository;
import it.uniroma3.siw.demospring.repository.OrdineRepository;
import it.uniroma3.siw.demospring.repository.RigaOrdinazioneRepository;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;
	@Autowired
	private OrdineRepository ordineRepository;
	@Autowired
	private RigaOrdinazioneRepository rigaOrdinazioneRepository;

	public FotoService(FotoRepository fotoRepository) {
		this.fotoRepository = fotoRepository;
	}

	public List<Foto> findAllFoto() {

		List<Foto> returnAllFoto = new ArrayList<>();

		Iterable<Foto> allFoto = fotoRepository.findAll();

		allFoto.forEach(returnAllFoto::add);

		return returnAllFoto;
	}

	public List<Foto> findAllFoto(List<Long> fotoIds) {
		List<Foto> returnAllFotoSelezionate = new ArrayList<>();

		for (Long id : fotoIds) {
			Foto foto = fotoRepository.findById(id).get();
			returnAllFotoSelezionate.add(foto);
		}

		return returnAllFotoSelezionate;
	}

	public List<Foto> getFotoPaginaSuccessivaDaVisualizzare(int pagina) {
		int ultimaFotoPagina = pagina*10;
		int primaFotoPagina = ultimaFotoPagina-10;

		List<Foto> fotoDellaPaginaIndicata = new ArrayList<Foto>();

		for(int i=primaFotoPagina; i<ultimaFotoPagina; i++) {
			try {
				fotoDellaPaginaIndicata.add(this.findFotoById((long) i));
			}catch (NoSuchElementException e) {
				System.out.println("Arrivati all'ultima pagina");
			}
		}

		return fotoDellaPaginaIndicata;
	}

	public Foto findFotoById(Long id) {
		return this.fotoRepository.findById(id).get();
	}

	public void aggiornaCarrelloConFotoSelezionateAdesso(
			String[] listFotoIds ,
			Model model, 
			HttpSession session) {
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
			List<Foto> fotoSelezionateAdesso = this.findAllFoto(fotoSelezionateAdessoIds);
			//Le aggiungo alle selezionate prima
			fotoSelezionatePrima.addAll(fotoSelezionateAdesso);
			//Metto le foto di prima e adesso in sessione come nuove selezionatePrima
			session.setAttribute("fotoSelezionatePrima", fotoSelezionatePrima);
		}



	}

	public void eliminaFotoSelezionateAdesso(String[] listFotoIds, Model model, HttpSession session) {
		List<Foto> fotoSelezionatePrima = (List<Foto>) session.getAttribute("fotoSelezionatePrima");
		if(fotoSelezionatePrima==null) {
			fotoSelezionatePrima = new ArrayList<Foto>();
		}
		List<Long> fotoSelezionateAdessoIds = new ArrayList<>();
		if (listFotoIds == null || listFotoIds.length == 0) {
			System.out.println("Foto richieste VUOTE");
		}
		else {
			for(String idStringFoto : listFotoIds) {
				Long idFoto = Long.parseLong(idStringFoto.trim());
				System.out.println("id Foto sel: " + idFoto);
				fotoSelezionateAdessoIds.add(idFoto);
			}
			List<Foto> fotoSelezionateAdesso = this.findAllFoto(fotoSelezionateAdessoIds);
			fotoSelezionatePrima.removeAll(fotoSelezionateAdesso);
			session.setAttribute("fotoSelezionatePrima", fotoSelezionatePrima);
		}
	}

	public void salvaMolteFoto(Ordine ordine, HttpSession session, HttpServletRequest request) {

		List<Foto> fotoDaSalvare = (List<Foto>) session.getAttribute("fotoSelezionatePrima");
		ordine = this.ordineRepository.save(ordine);
		List<RigaOrdinazione> righeOrdinazione = new ArrayList<RigaOrdinazione>();
		for(Foto foto: fotoDaSalvare) {
			RigaOrdinazione rigaOrdinazione = new RigaOrdinazione();
			rigaOrdinazione.setFoto(foto);
			rigaOrdinazione.setOrdine(ordine);
			righeOrdinazione.add(rigaOrdinazione);
		}
		ordine.setRigheOrdinazione(righeOrdinazione);
		this.rigaOrdinazioneRepository.saveAll(righeOrdinazione);
	}

	public boolean esiste(@Valid Foto foto) {
		return this.fotoRepository.findByLink(foto.getLink())!=null;
	}

	public List<Foto> tutteFoto() {
		return (List<Foto>) this.fotoRepository.findAll();
	}
	
	public void inserisci(Foto foto) {
		this.fotoRepository.save(foto);
	}


}
