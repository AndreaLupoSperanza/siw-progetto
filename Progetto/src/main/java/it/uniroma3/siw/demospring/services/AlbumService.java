package it.uniroma3.siw.demospring.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.demospring.model.Album;
import it.uniroma3.siw.demospring.repository.AlbumRepository;

@Service
public class AlbumService {
	@Autowired
	private AlbumRepository albumRepository;

	public boolean esiste(@Valid Album album) {
		return (this.albumRepository.findByNome(album.getNome())!=null);
	}

	public void inserisci(@Valid Album album) {
		this.albumRepository.save(album);
	}

	public List<Album> tuttiGliAlbum() {
		return (List<Album>) this.albumRepository.findAll();
	}

	public Album getSingoloAlbum(Long id) {
		return this.albumRepository.findById(id).get();
	}

	public Album findAlbumById(Long id) {
		return this.albumRepository.findById(id).get();
	}
	

}
