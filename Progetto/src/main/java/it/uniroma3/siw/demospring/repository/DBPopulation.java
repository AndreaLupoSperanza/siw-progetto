package it.uniroma3.siw.demospring.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Autore;
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
	
	private void addAll() {
		System.out.print("Aggiungo utenti");
		Autore aut1 = null, aut2 = null;
		Foto f1,f2,f3,f4,f5,f6,f7,f8,f9,f10;
		Album alb1 = null,alb2 = null;
		/*Inizializzo Foto*/
		f1 = fotoSet("GiaccaSuTela", alb1, aut1, "");
		f2 = fotoSet("GiaccaSuTela", alb1, aut1, "");
		f3 = fotoSet("GiaccaSuTela", alb1, aut1, "");
		f4 = fotoSet("GiaccaSuTela", alb1, aut1, "");
		f5 = fotoSet("GiaccaSuTela", alb1, aut1, "");
		f6 = fotoSet("GiaccaSuTela", alb2, aut2, "");
		f7 = fotoSet("GiaccaSuTela", alb2, aut2, "");
		f8 = fotoSet("GiaccaSuTela", alb2, aut2, "");
		f9 = fotoSet("GiaccaSuTela", alb2, aut2, "");
		f10 = fotoSet("GiaccaSuTela", alb2, aut2, "");
		
		/*Liste di foto in un album*/
		List<Foto> listaFotoAlbum1 = new ArrayList<Foto>();
		listaFotoAlbum1.add(f1);
		listaFotoAlbum1.add(f2);
		listaFotoAlbum1.add(f3);
		listaFotoAlbum1.add(f4);
		listaFotoAlbum1.add(f5);
		List<Foto> listaFotoAlbum2 = new ArrayList<Foto>();
		listaFotoAlbum2.add(f6);
		listaFotoAlbum2.add(f7);
		listaFotoAlbum2.add(f8);
		listaFotoAlbum2.add(f9);
		listaFotoAlbum2.add(f10);
		
		/*Inizializzo Album*/
		alb1 = albumSet("Gattini teneri", listaFotoAlbum1, aut1);
		alb2 = albumSet("Porcellini teneri", listaFotoAlbum2, aut2);
		
		/*Lista album di un autore*/
		List<Album> listaAlbum1 = new ArrayList<Album>();
		listaAlbum1.add(alb1);
		List<Album> listaAlbum2 = new ArrayList<Album>();
		listaAlbum2.add(alb2);
		
		/*Inizializzo Autore*/
		aut1 = autoreSet("Enea", listaAlbum1);
		aut2 = autoreSet("Andrea", listaAlbum2);
		
		/*Salvataggio di tutte le entità*/
		
		/*Salvataggio autori*/
		this.autoreRepository.save(aut1);
		this.autoreRepository.save(aut2);
		/*Salvataggio album*/
		this.albumRepository.save(alb1);
		this.albumRepository.save(alb2);
		/*Salvataggio foto*/
		this.fotoRepository.save(f1);
		this.fotoRepository.save(f2);
		this.fotoRepository.save(f3);
		this.fotoRepository.save(f4);
		this.fotoRepository.save(f5);
		this.fotoRepository.save(f6);
		this.fotoRepository.save(f7);
		this.fotoRepository.save(f8);
		this.fotoRepository.save(f9);
		this.fotoRepository.save(f10);

	}
	
	private Foto fotoSet(String nomeFoto,Album albFoto, Autore autFoto, String linkFoto) {
		Foto f = new Foto();
		f.setNome(nomeFoto);
		f.setAlbum(albFoto);
		f.setAutore(autFoto);
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
	private Autore autoreSet(String nomeAutore, List<Album> albumAutore) {
		Autore a = new Autore();
		a.setNome(nomeAutore);
		a.setAlbum(albumAutore);
		return a;
	}
	
	
	
}
