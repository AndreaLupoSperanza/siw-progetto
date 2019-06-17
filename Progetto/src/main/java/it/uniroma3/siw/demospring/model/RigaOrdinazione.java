package it.uniroma3.siw.demospring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import it.uniroma3.siw.demospring.repository.FotoRepository;

@Entity
public class RigaOrdinazione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne 
	private Foto foto;
	
	

}
