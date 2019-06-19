package it.uniroma3.siw.demospring.controller;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

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
	@Autowired
	private AmazonS3 amazonS3Client;

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

	@RequestMapping(value = "/tuttiAutoriAdmin", method = RequestMethod.GET)
	public String getTuttiAutori(Model model) {
		model.addAttribute("autori", this.autoreService.tuttiGliAutore());
		return "tuttiGliAutoriAdmin.html";
	}

	@RequestMapping(value = "/tuttiAlbumAdmin", method = RequestMethod.GET)
	public String getAlbumAdmin(Model model) {
		model.addAttribute("gliAlbum", this.albumService.tuttiGliAlbum());
		return "tuttiGliAlbumAdmin.html";
	}

	@RequestMapping(value = "/tuttiFotoAdmin", method = RequestMethod.GET)
	public String getFotoAdmin(Model model) {
		model.addAttribute("leFoto", this.fotoService.findAllFoto());
		return "tutteLeFotoAdmin.html";
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
		session.setAttribute("AutoreAlbum", autoreSel);
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
		session.setAttribute("AlbumFoto", albumSel);
		model.addAttribute("foto", new Foto());
		return "addFoto.html";
	}

	@RequestMapping(value = "/addAlbum", method = RequestMethod.POST)
	public String addAutore(@Valid @ModelAttribute("album") Album album,
			Model model, BindingResult bindingResult,
			HttpSession session) {
		Autore autore = (Autore) session.getAttribute("AutoreAlbum");
		this.albumValidator.validate(album, bindingResult);
		if(!bindingResult.hasErrors()) {
			if(this.albumService.esiste(album)) {
				model.addAttribute("esiste", "Questo album esiste già");
			}else {
				album.setAutore(autore);
				this.albumService.inserisci(album);
				model.addAttribute("gliAlbum", this.albumService.tuttiGliAlbum());
				return "tuttiGliAlbumAdmin.html";
			}
		}
		model.addAttribute("album", album);
		return "addAlbum";
	}

	@RequestMapping(value = "/addFoto", method = RequestMethod.POST)
	public String addAlbum(@Valid @ModelAttribute("foto") Foto foto,
			Model model, 
			BindingResult bindingResult,
			HttpSession session) {

		Album albumSel = (Album) session.getAttribute("AlbumFoto");
		foto.setAlbum(albumSel);
		this.fotoValidator.validate(foto, bindingResult);
		if(!bindingResult.hasErrors()) {
			if(this.fotoService.esiste(foto)) {
				model.addAttribute("esiste", "Una foto con questo nome esiste già");
			}else {
				this.fotoService.inserisci(foto);
				model.addAttribute("leFoto", this.fotoService.tutteFoto());
				return "tutteFotoAdmin.html";
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
				model.addAttribute("autori", this.autoreService.tuttiGliAutore());
				return "tuttiGliAutoriAdmin.html";
			}
		}
		model.addAttribute("autore", autore);
		return "addAutore";
	}

	@RequestMapping(value = "/addFotoLink", method = RequestMethod.POST)
	public String addFoto(@Valid @ModelAttribute("foto") Foto foto,
			Model model, BindingResult bindingResult) {
		this.fotoValidator.validate(foto, bindingResult);
		if(!bindingResult.hasErrors()) {
			if(this.fotoService.esiste(foto)) {
				model.addAttribute("esiste", "Una foto con questo link esiste già");
			}else {

				this.fotoService.inserisci(foto);
				model.addAttribute("foto", this.fotoService.tutteFoto());
				return "tutteLeFoto";
			}
		}
		model.addAttribute("foto", foto);
		return "fotoForm";
	}

	@RequestMapping(value="/fotoAdmin/{id}", method = RequestMethod.GET)
	public String getFoto(@PathVariable("id") Long id, Model model){
		if (id!=null) {
			model.addAttribute("foto",this.fotoService.findFotoById(id));
			return "fotoAdmin.html";
		}
		else {
			return "amministratore";
		}
	}

	@RequestMapping(value="/albumAdmin/{id}", method = RequestMethod.GET)
	public String getAlbum(@PathVariable("id") Long id, Model model){
		if (id!=null) {
			model.addAttribute("album",this.albumService.findAlbumById(id));
			return "albumAdmin.html";
		}
		else {
			return "amministratore";
		}
	}

	@RequestMapping(value="/autoreAdmin/{id}", method = RequestMethod.GET)
	public String getAutore(@PathVariable("id") Long id, Model model){
		if (id!=null) {
			model.addAttribute("autore",this.autoreService.findAutoreById(id));
			return "autoreAdmin.html";
		}
		else {
			return "amministratore";
		}
	}

	@Bean
	public AmazonS3 amazonS3Client(AWSCredentialsProvider credentialsProvider,
			@Value("${cloud.aws.region.static}") String region) {
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(credentialsProvider)
				.withRegion(Regions.EU_CENTRAL_1)
				.build();
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFoto(@Valid @ModelAttribute("foto") Foto foto, 
			Model model,
			BindingResult bindingResult,
			@RequestParam("file") MultipartFile file,
			HttpSession session) {
		Album albumSel = (Album) session.getAttribute("AlbumFoto");
		this.fotoValidator.validate(foto, bindingResult);
		if(!bindingResult.hasErrors()) {
			File convFile = null;
			try {
				convFile=this.convertMultiPartToFile(file);
			}catch (IOException e) {
				return "erroreFoto";
			}
			//per salvare su amazon
			this.uploadFileToS3bucket("it.siw.uniroma3.cuomo", convFile, foto.getNome());
			//per prendere l'url dove ha salvato la risorda
			String link=amazonS3Client.getUrl("it.siw.uniroma3.cuomo", foto.getNome()).toString();
			foto.setLink(link);
			foto.setAlbum(albumSel);
			System.out.println(albumSel.getNome());
			this.fotoService.inserisci(foto);
			model.addAttribute("leFoto", this.fotoService.findAllFoto());
		}
		return "tutteLeFotoAdmin.html";
	}

	private void uploadFileToS3bucket(String bucketName, File file, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
	}


	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}




}
