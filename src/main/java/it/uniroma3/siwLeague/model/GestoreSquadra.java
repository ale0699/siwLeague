package it.uniroma3.siwLeague.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class GestoreSquadra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idGestoreSquadra;
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Credenziali credenziali;
	
	@OneToMany(mappedBy = "teamManager")
	private List<Squadra> squadreGestite;

	public Long getIdGestoreSquadra() {
		return idGestoreSquadra;
	}

	public void setIdGestoreSquadra(Long idGestoreSquadra) {
		this.idGestoreSquadra = idGestoreSquadra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Credenziali getCredenziali() {
		return credenziali;
	}

	public void setCredenziali(Credenziali credenziali) {
		this.credenziali = credenziali;
	}

	public List<Squadra> getSquadreGestite() {
		return squadreGestite;
	}

	public void setSquadreGestite(List<Squadra> squadreGestite) {
		this.squadreGestite = squadreGestite;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, credenziali, idGestoreSquadra, nome, squadreGestite);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GestoreSquadra other = (GestoreSquadra) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(credenziali, other.credenziali)
				&& Objects.equals(idGestoreSquadra, other.idGestoreSquadra) && Objects.equals(nome, other.nome)
				&& Objects.equals(squadreGestite, other.squadreGestite);
	}

	@Override
	public String toString() {
		return "GestoreSquadra [idGestoreSquadra=" + idGestoreSquadra + ", nome=" + nome + ", cognome=" + cognome
				+ ", credenziali=" + credenziali + ", squadreGestite=" + squadreGestite + "]";
	}
}
