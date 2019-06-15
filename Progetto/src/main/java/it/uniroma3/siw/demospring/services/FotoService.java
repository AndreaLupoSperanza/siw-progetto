package it.uniroma3.siw.demospring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.demospring.model.Foto;
import it.uniroma3.siw.demospring.repository.FotoRepository;

@Service
public class FotoService {
	
	@Autowired
	 private FotoRepository fotoRepository;
	
	public Foto fotoPerId(Long id) {
		return this.fotoRepository.findById(id).get();
	}
	
	public List<Foto> tutte(){
		return (List<Foto>) this.fotoRepository.findAll();
	}
}
