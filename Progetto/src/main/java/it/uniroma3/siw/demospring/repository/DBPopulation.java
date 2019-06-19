package it.uniroma3.siw.demospring.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Autore;
import it.uniroma3.siw.demospring.model.Dipendente;
import it.uniroma3.siw.demospring.model.Foto;

/*
 * è un componente della nostra applicazione
 */
@Component
public class DBPopulation implements ApplicationRunner{

	@Autowired
	private AlbumRepository albumRepository;
	@Autowired
	private AutoreRepository autoreRepository;
	@Autowired
	private FotoRepository fotoRepository;
	@Autowired
	private DipendenteRepository dipendenteRepository;

	public void run(ApplicationArguments args) throws Exception {
		this.deleteAll();
		this.addAll();
	}
	
	private void deleteAll() {
		System.out.print("Cancello tutto");
		albumRepository.deleteAll();
		autoreRepository.deleteAll();
		fotoRepository.deleteAll();
	}
	
	private void addAll() throws InterruptedException {
		Autore aut1 = null, aut2 = null;
		Album alb1 = null,alb2 = null;
		Foto f1,f2,f3,f4,f5,f6,f7,f8,f9,f10;
		Dipendente dipendente1;
		
		dipendente1 = dipendenteSet("Andrea", "Giaccone", "giacca@gmail.com","1234");
		
		/*La generazione delle liste non è importante nel salvataggio iniziale */
		
		dipendente1 = this.dipendenteRepository.save(dipendente1);
		/*Inizializzo Autore*/
		aut1 = autoreSet("Enea", "Ce", null);
		aut2 = autoreSet("Andrea", "Giaccone", null);
		
		/*Salvataggio autori*/
		System.out.println("Autore id:"+aut1.getId()+" Autore rif: " + aut1.toString());
		aut1 = this.autoreRepository.save(aut1);
		System.out.println("Autore id:"+aut1.getId()+" Autore rif: " + aut1.toString());
		aut2 = this.autoreRepository.save(aut2);
		
		/*Inizializzo Album*/
		alb1 = albumSet("Gattini teneri", null, aut1);
		alb2 = albumSet("Porcellini teneri", null, aut2);

		
		/*Salvataggio album*/
		alb1 = this.albumRepository.save(alb1);
		alb2 = this.albumRepository.save(alb2);
		
	
		/*Inizializzo Foto*/
		f1 = fotoSet("GiaccaSuTela1", alb1, "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		f2 = fotoSet("GiaccaSuTela2", alb1, "https://besthqwallpapers.com/img/original/49380/bengal-cat-exotic-jungle-cats-4k-small-gray-kitten-pets.jpg");
		f3 = fotoSet("GiaccaSuTela3", alb1, "https://footage.framepool.com/shotimg/qf/298838331-living-being-european-wildcat-kitten-feeding-brood-care.jpg");
		f4 = fotoSet("GiaccaSuTela4", alb1, "https://besthqwallpapers.com/Uploads/30-10-2017/26294/thumb2-aegean-cat-4k-muzzle-cute-animals-cats.jpg");
		f5 = fotoSet("GiaccaSuTela5", alb1, "https://forzoo.ru/assets/vodob-vi-695x652.png");
		f6 = fotoSet("GiaccaSuTela6", alb2, "https://images8.alphacoders.com/532/532996.jpg");
		f7 = fotoSet("GiaccaSuTela7", alb2, "http://4everstatic.com/imagenes/850xX/animales/gatos/gato-negro,-ojos-verdes-210920.jpg");
		f8 = fotoSet("GiaccaSuTela8", alb2, "https://st3.depositphotos.com/14828974/17844/v/600/depositphotos_178440952-stock-video-close-up-4k-green-eyes.jpg");
		f9 = fotoSet("GiaccaSuTela9", alb2, "https://i.pinimg.com/originals/75/ff/9b/75ff9b08607e1302415712c317591d53.png");
		f10 = fotoSet("GiaccaSuTela10", alb2, "https://besthqwallpapers.com/Uploads/6-5-2018/51316/thumb2-4k-scottish-fold-cat-domestic-cat-gray-cat-close-up.jpg");

		/*Salvataggio foto*/
		f1 = this.fotoRepository.save(f1);
		f2 = this.fotoRepository.save(f2);
		f3 = this.fotoRepository.save(f3);
		f4 = this.fotoRepository.save(f4);
		f5 = this.fotoRepository.save(f5);
		f6 = this.fotoRepository.save(f6);
		f7 = this.fotoRepository.save(f7);
		f8 = this.fotoRepository.save(f8);
		f9 = this.fotoRepository.save(f9);
		f10 = this.fotoRepository.save(f10);

	}
	
	

	private Foto fotoSet(String nomeFoto,Album albFoto, String linkFoto) {
		Foto f = new Foto();
		f.setNome(nomeFoto);
		f.setAlbum(albFoto);
		f.setLink(linkFoto);
		return f;
	}
	
	private Album albumSet(String nomeAlbum, List<Foto> fotoAlbum, Autore autoreAlbum) {
		Album a = new Album();
		a.setNome(nomeAlbum);
		a.setFotografie(fotoAlbum);
		a.setAutore(autoreAlbum);
		return a;
	}
	private Autore autoreSet(String nomeAutore, String cognomeAutore, List<Album> albumAutore) {
		Autore a = new Autore();
		a.setNome(nomeAutore);
		a.setCognome(cognomeAutore);
		a.setAlbum(albumAutore);
		return a;
	}
	
	private Dipendente dipendenteSet(String nome, String cognome, String email, String password) {
		Dipendente d = new Dipendente();
		d.setNome(nome);
		d.setCognome(cognome);
		d.setEmail(email);
		d.setPassword(password);
		return d;
	}
	
}
