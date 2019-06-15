package it.uniroma3.siw.demospring.services;

import it.uniroma3.siw.demospring.model.Foto;
import it.uniroma3.siw.demospring.repository.FotoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FotoService {

	
    private final FotoRepository fotoRepository;

    public FotoService(FotoRepository fotoRepository) {
        this.fotoRepository = fotoRepository;
    }

    public List<Foto> findAllFoto() {

        List<Foto> returnAllFoto = new ArrayList<>();

        Iterable<Foto> allFoto = fotoRepository.findAll();

        allFoto.forEach(returnAllFoto::add);

        return returnAllFoto;
    }

    public List<Foto> findAllFoto(List<Long> fotoIds) {
        List<Foto> returnAllFotoSelezionate = new ArrayList<>();

        for (Long id : fotoIds) {
            Foto foto = fotoRepository.findById(id).get();
            returnAllFotoSelezionate.add(foto);
        }

        return returnAllFotoSelezionate;
    }

	public List<Foto> getSuccessiveFotoDaVisualizzare() {
		// TODO Auto-generated method stub
		return null;
	}
}
