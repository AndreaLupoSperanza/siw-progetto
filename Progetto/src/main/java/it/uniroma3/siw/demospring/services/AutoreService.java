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

}
