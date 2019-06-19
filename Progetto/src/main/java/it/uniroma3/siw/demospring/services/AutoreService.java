package it.uniroma3.siw.demospring.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.demospring.model.Autore;
import it.uniroma3.siw.demospring.repository.AutoreRepository;
@Service
public class AutoreService {
	@Autowired
	private AutoreRepository autoreRepository;

	public void inserisci(@Valid Autore autore) {
		this.autoreRepository.save(autore);
	}

	public List<Autore> tuttiGliAutore() {
		return (List<Autore>) this.autoreRepository.findAll();
	}

	public boolean esiste(Autore autore) {
		return ((this.autoreRepository.findByNome(autore.getNome()).size()>0)&&(this.autoreRepository.findByCognome(autore.getCognome()).size()>0));
	}

	public Autore getSingoloAutore(Long id) {
		return this.autoreRepository.findById(id).get();
	}

	public Autore findAutoreById(Long id) {
		return this.autoreRepository.findById(id).get();
	}

	public List<Autore> findByNome(String nome) {
		return this.autoreRepository.findByNome(nome);
	}

	public List<Autore> findByCognome(String cognome) {
		return this.autoreRepository.findByCognome(cognome);
	}

}
