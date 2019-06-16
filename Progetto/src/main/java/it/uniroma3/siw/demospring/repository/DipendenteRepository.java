package it.uniroma3.siw.demospring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.demospring.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>{

	public List<Dipendente> findByNome(String nome);
}
