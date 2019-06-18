package it.uniroma3.siw.demospring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.demospring.model.Ordine;
import it.uniroma3.siw.demospring.repository.OrdineRepository;

@Service
public class OrdineService {
	
	@Autowired
	private OrdineRepository ordineRepository;

	public Ordine ordinePerId(Long id) {
		return this.ordineRepository.findById(id).get();
	}

	public List<Ordine> tuttiOrdini() {
		return (List<Ordine>) this.ordineRepository.findAll();
	}

}
