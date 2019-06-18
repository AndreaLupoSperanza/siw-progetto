package it.uniroma3.siw.demospring.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Ordine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeAcquirente;
	private String cognomeAcquirente;
	private String numeroTelefonico;
	@OneToMany(mappedBy = "ordine")
	private List<RigaOrdinazione> righeOrdinazione;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeAcquirente() {
		return nomeAcquirente;
	}
	public void setNomeAcquirente(String nomeAcquirente) {
		this.nomeAcquirente = nomeAcquirente;
	}
	public String getCognomeAcquirente() {
		return cognomeAcquirente;
	}
	public void setCognomeAcquirente(String cognomeAcquirente) {
		this.cognomeAcquirente = cognomeAcquirente;
	}
	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}
	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}
	public List<RigaOrdinazione> getRigheOrdinazione() {
		return righeOrdinazione;
	}
	public void setRigheOrdinazione(List<RigaOrdinazione> righeOrdinazione) {
		this.righeOrdinazione = righeOrdinazione;
	}
	
	
	
}
