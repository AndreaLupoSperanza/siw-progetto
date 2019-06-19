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
import it.uniroma3.siw.demospring.model.Ordine;
import it.uniroma3.siw.demospring.model.RigaOrdinazione;

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
	@Autowired
	private OrdineRepository ordineRepository;
	@Autowired
	private RigaOrdinazioneRepository rigaOrdinazioneRepository;

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
		Autore aut1,aut2,aut3,aut4,aut5,aut6;
		Album alb1,alb2,alb3,alb4,alb5,alb6,alb7,alb8,alb9;
		Dipendente dipendente1;

		dipendente1 = dipendenteSet("Paolo", "Merialdo", "merialdo@dia.uniroma3.it","siw");

		/*La generazione delle liste non è importante nel salvataggio iniziale */

		dipendente1 = this.dipendenteRepository.save(dipendente1);
		/*Inizializzo Autore*/
		aut1 = autoreSet("Enea", "Cekorja", null);
		aut2 = autoreSet("Lucciola", "Brillante", null);
		aut3 = autoreSet("Zarion", "Hegan", null);
		aut4 = autoreSet("Renzo", "Piano", null);
		aut5 = autoreSet("Andrea", "Speranza", null);
		aut6 = autoreSet("Eccì", "Salute", null);

		/*Salvataggio autori*/
		aut1 = this.autoreRepository.save(aut1);
		aut2 = this.autoreRepository.save(aut2);
		aut3 = this.autoreRepository.save(aut3);
		aut4 = this.autoreRepository.save(aut4);
		aut5 = this.autoreRepository.save(aut5);
		aut6 = this.autoreRepository.save(aut6);

		/*Inizializzo Album*/
		alb1 = albumSet("Gattini1", null, aut1);
		alb2 = albumSet("Gattini2", null, aut2);
		alb3 = albumSet("Gattini3", null, aut3);
		alb4 = albumSet("Gattini4", null, aut4);
		alb5 = albumSet("Gattini5", null, aut5);
		alb6 = albumSet("Gattini6", null, aut6);
		alb7 = albumSet("Gattini7", null, aut1);
		alb8 = albumSet("Gattini8", null, aut2);
		alb9 = albumSet("Gattini9", null, aut3);


		/*Salvataggio album*/
		alb1 = this.albumRepository.save(alb1);
		alb2 = this.albumRepository.save(alb2);
		alb3 = this.albumRepository.save(alb3);
		alb4 = this.albumRepository.save(alb4);
		alb5 = this.albumRepository.save(alb5);
		alb6 = this.albumRepository.save(alb6);
		alb7 = this.albumRepository.save(alb7);
		alb8 = this.albumRepository.save(alb8);
		alb9 = this.albumRepository.save(alb9);



		/*Inizializzo Foto*/
		Foto 	f1 = fotoSet("Gatto1", this.albumRepository.findById((long) 1).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto 	f2 = fotoSet("Gatto2", this.albumRepository.findById((long) 1).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto 	f3 = fotoSet("Gatto3", this.albumRepository.findById((long) 1).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f4 = fotoSet("Gatto4", this.albumRepository.findById((long) 1).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f5 = fotoSet("Gatto5", this.albumRepository.findById((long) 1).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f6 = fotoSet("Gatto6", this.albumRepository.findById((long) 1).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f7 = fotoSet("Gatto7", this.albumRepository.findById((long) 1).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto 	f8 = fotoSet("Gatto8", this.albumRepository.findById((long) 1).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f9 = fotoSet("Gatto9", this.albumRepository.findById((long) 1).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f10 = fotoSet("Gatto10", this.albumRepository.findById((long) 2).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f11 = fotoSet("Gatto11", this.albumRepository.findById((long) 2).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f12 = fotoSet("Gatto12", this.albumRepository.findById((long) 2).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f13 = fotoSet("Gatto13", this.albumRepository.findById((long) 2).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f14 = fotoSet("Gatto14", this.albumRepository.findById((long) 2).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f15 = fotoSet("Gatto15", this.albumRepository.findById((long) 3).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f16 = fotoSet("Gatto16", this.albumRepository.findById((long) 3).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f17 = fotoSet("Gatto17", this.albumRepository.findById((long) 3).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f18 = fotoSet("Gatto18", this.albumRepository.findById((long) 3).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f19 = fotoSet("Gatto19", this.albumRepository.findById((long) 3).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f20 = fotoSet("Gatto20", this.albumRepository.findById((long) 4).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f21 = fotoSet("Gatto21", this.albumRepository.findById((long) 4).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f22 = fotoSet("Gatto22", this.albumRepository.findById((long) 4).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f23 = fotoSet("Gatto23", this.albumRepository.findById((long) 4).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f24 = fotoSet("Gatto24", this.albumRepository.findById((long) 5).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f25 = fotoSet("Gatto25", this.albumRepository.findById((long) 5).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f26 = fotoSet("Gatto26", this.albumRepository.findById((long) 5).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f27 = fotoSet("Gatto27", this.albumRepository.findById((long) 5).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f28 = fotoSet("Gatto28", this.albumRepository.findById((long) 6).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f29 = fotoSet("Gatto29", this.albumRepository.findById((long) 6).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f30 = fotoSet("Gatto30", this.albumRepository.findById((long) 6).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f31 = fotoSet("Gatto31", this.albumRepository.findById((long) 7).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f32 = fotoSet("Gatto32", this.albumRepository.findById((long) 7).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f33 = fotoSet("Gatto33", this.albumRepository.findById((long) 7).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f34 = fotoSet("Gatto34", this.albumRepository.findById((long) 7).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f35 = fotoSet("Gatto35", this.albumRepository.findById((long) 7).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f36 = fotoSet("Gatto36", this.albumRepository.findById((long) 7).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f37 = fotoSet("Gatto37", this.albumRepository.findById((long) 8).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f38 = fotoSet("Gatto38", this.albumRepository.findById((long) 8).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f39 = fotoSet("Gatto39", this.albumRepository.findById((long) 8).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");
		Foto	f40 = fotoSet("Gatto40", this.albumRepository.findById((long) 9).get(), "https://i.ytimg.com/vi/NBqO322JRyI/maxresdefault.jpg");


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
		f11 = this.fotoRepository.save(f11);
		f12 = this.fotoRepository.save(f12);
		f13 = this.fotoRepository.save(f13);
		f14 = this.fotoRepository.save(f14);
		f15 = this.fotoRepository.save(f15);
		f16 = this.fotoRepository.save(f16);
		f17 = this.fotoRepository.save(f17);
		f18 = this.fotoRepository.save(f18);
		f19 = this.fotoRepository.save(f19);
		f20 = this.fotoRepository.save(f20);
		f21 = this.fotoRepository.save(f21);
		f22 = this.fotoRepository.save(f22);
		f23 = this.fotoRepository.save(f23);
		f24 = this.fotoRepository.save(f24);
		f25 = this.fotoRepository.save(f25);
		f26 = this.fotoRepository.save(f26);
		f27 = this.fotoRepository.save(f27);
		f28 = this.fotoRepository.save(f28);
		f29 = this.fotoRepository.save(f29);
		f30 = this.fotoRepository.save(f30);
		f31 = this.fotoRepository.save(f31);
		f32 = this.fotoRepository.save(f32);
		f33 = this.fotoRepository.save(f33);
		f34 = this.fotoRepository.save(f34);
		f35 = this.fotoRepository.save(f35);
		f36 = this.fotoRepository.save(f36);
		f37 = this.fotoRepository.save(f37);
		f38 = this.fotoRepository.save(f38);
		f39 = this.fotoRepository.save(f39);
		f40 = this.fotoRepository.save(f40);
		
		
		Ordine ordine1 = new Ordine();
		Ordine ordine2 = new Ordine();
		Ordine ordine3 = new Ordine();
		Ordine ordine4 = new Ordine();
		Ordine ordine5 = new Ordine();
		
		ordine1.setNomeAcquirente("Giovanni");
		ordine2.setNomeAcquirente("Harnold");
		ordine3.setNomeAcquirente("Bello");
		ordine4.setNomeAcquirente("Qua");
		ordine5.setNomeAcquirente("Emily");
		
		ordine1.setCognomeAcquirente("Muciaccia");
		ordine2.setCognomeAcquirente("sfffffffazzneggher");
		ordine3.setCognomeAcquirente("Figo");
		ordine4.setCognomeAcquirente("La Zampa");
		ordine5.setCognomeAcquirente("Rataikwlijlijmklnm");
		
		ordine1.setNumeroTelefonico("0674328734");
		ordine2.setNumeroTelefonico("3382354671");
		ordine3.setNumeroTelefonico("3315476342");
		ordine4.setNumeroTelefonico("3278765342");
		ordine5.setNumeroTelefonico("3319867543");
		
		ordine1 = this.ordineRepository.save(ordine1);
		ordine2 = this.ordineRepository.save(ordine2);
		ordine3 = this.ordineRepository.save(ordine3);
		ordine4 = this.ordineRepository.save(ordine4);
		ordine5 = this.ordineRepository.save(ordine5);
		
		RigaOrdinazione rO1 = new RigaOrdinazione();
		RigaOrdinazione rO2 = new RigaOrdinazione();
		RigaOrdinazione rO3 = new RigaOrdinazione();
		RigaOrdinazione rO4 = new RigaOrdinazione();
		RigaOrdinazione rO5 = new RigaOrdinazione();
		RigaOrdinazione rO6 = new RigaOrdinazione();
		RigaOrdinazione rO7 = new RigaOrdinazione();
		RigaOrdinazione rO8 = new RigaOrdinazione();
		RigaOrdinazione rO9 = new RigaOrdinazione();
		RigaOrdinazione rO10 = new RigaOrdinazione();
		RigaOrdinazione rO11 = new RigaOrdinazione();
		RigaOrdinazione rO12 = new RigaOrdinazione();
		RigaOrdinazione rO13 = new RigaOrdinazione();
		RigaOrdinazione rO14 = new RigaOrdinazione();
		RigaOrdinazione rO15 = new RigaOrdinazione();
		
		rO1.setOrdine(this.ordineRepository.findById((long) 1).get());
		rO2.setOrdine(this.ordineRepository.findById((long) 1).get());
		rO3.setOrdine(this.ordineRepository.findById((long) 1).get());
		rO4.setOrdine(this.ordineRepository.findById((long) 1).get());
		rO5.setOrdine(this.ordineRepository.findById((long) 1).get());
		rO6.setOrdine(this.ordineRepository.findById((long) 2).get());
		rO7.setOrdine(this.ordineRepository.findById((long) 2).get());
		rO8.setOrdine(this.ordineRepository.findById((long) 2).get());
		rO9.setOrdine(this.ordineRepository.findById((long) 2).get());
		rO10.setOrdine(this.ordineRepository.findById((long) 3).get());
		rO11.setOrdine(this.ordineRepository.findById((long) 3).get());
		rO12.setOrdine(this.ordineRepository.findById((long) 3).get());
		rO13.setOrdine(this.ordineRepository.findById((long) 4).get());
		rO14.setOrdine(this.ordineRepository.findById((long) 4).get());
		rO15.setOrdine(this.ordineRepository.findById((long) 5).get());
		
		rO1.setFoto(this.fotoRepository.findById((long) 1).get());
		rO2.setFoto(this.fotoRepository.findById((long) 2).get());
		rO3.setFoto(this.fotoRepository.findById((long) 3).get());
		rO4.setFoto(this.fotoRepository.findById((long) 4).get());
		rO5.setFoto(this.fotoRepository.findById((long) 5).get());
		rO6.setFoto(this.fotoRepository.findById((long) 6).get());
		rO7.setFoto(this.fotoRepository.findById((long) 7).get());
		rO8.setFoto(this.fotoRepository.findById((long) 8).get());
		rO9.setFoto(this.fotoRepository.findById((long) 9).get());
		rO10.setFoto(this.fotoRepository.findById((long) 10).get());
		rO11.setFoto(this.fotoRepository.findById((long) 11).get());
		rO12.setFoto(this.fotoRepository.findById((long) 12).get());
		rO13.setFoto(this.fotoRepository.findById((long) 13).get());
		rO14.setFoto(this.fotoRepository.findById((long) 14).get());
		rO15.setFoto(this.fotoRepository.findById((long) 15).get());
		
		rO1 = this.rigaOrdinazioneRepository.save(rO1);
		rO2 = this.rigaOrdinazioneRepository.save(rO2);
		rO3 = this.rigaOrdinazioneRepository.save(rO3);
		rO4 = this.rigaOrdinazioneRepository.save(rO4);
		rO5 = this.rigaOrdinazioneRepository.save(rO5);
		rO6 = this.rigaOrdinazioneRepository.save(rO6);
		rO7 = this.rigaOrdinazioneRepository.save(rO7);
		rO8 = this.rigaOrdinazioneRepository.save(rO8);
		rO9 = this.rigaOrdinazioneRepository.save(rO9);
		rO10 = this.rigaOrdinazioneRepository.save(rO10);
		rO11 = this.rigaOrdinazioneRepository.save(rO11);
		rO12 = this.rigaOrdinazioneRepository.save(rO12);
		rO13 = this.rigaOrdinazioneRepository.save(rO13);
		rO14 = this.rigaOrdinazioneRepository.save(rO14);
		rO15 = this.rigaOrdinazioneRepository.save(rO15);

		
		

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
