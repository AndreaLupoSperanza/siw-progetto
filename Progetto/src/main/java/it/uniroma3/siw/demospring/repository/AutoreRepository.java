package it.uniroma3.siw.demospring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.demospring.model.Autore;

public interface AutoreRepository extends CrudRepository<Autore, Long> {

	public List<Autore> findByNome(String nome);
	
	public List<Autore> findByCognome(String cognome);

}
