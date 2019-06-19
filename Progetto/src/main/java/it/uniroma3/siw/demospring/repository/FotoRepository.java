package it.uniroma3.siw.demospring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.model.Foto;
import it.uniroma3.siw.demospring.model.Autore;

public interface FotoRepository extends CrudRepository<Foto, Long>{

	public List<Foto> findByAlbum(Album album);

	public Foto findByLink(String link);

	public Foto findByNome(String nome);
}
