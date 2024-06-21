package it.uniroma3.siwLeague.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Giocatore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idGiocatore;
	@NotBlank
	private String nome;
	private String cognome;
	private int numeroMaglia;
	private LocalDate dataNascita;
	
	private Integer golSegnati;
	
	@ManyToOne
	private Squadra squadra;

	public Long getIdGiocatore() {
		return idGiocatore;
	}

	public void setIdGiocatore(Long idGiocatore) {
		this.idGiocatore = idGiocatore;
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

	public int getNumeroMaglia() {
		return numeroMaglia;
	}

	public void setNumeroMaglia(int numeroMaglia) {
		this.numeroMaglia = numeroMaglia;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}
	
	public void setGolSegnati(Integer golSegnati) {
		this.golSegnati = golSegnati;
	}

	public Integer getGolSegnati() {
		return this.golSegnati;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataNascita, idGiocatore, nome, numeroMaglia, squadra);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
				&& Objects.equals(idGiocatore, other.idGiocatore) && Objects.equals(nome, other.nome)
				&& numeroMaglia == other.numeroMaglia && Objects.equals(squadra, other.squadra);
	}

	@Override
	public String toString() {
		return "Giocatore [idGiocatore=" + idGiocatore + ", nome=" + nome + ", cognome=" + cognome + ", numeroMaglia="
				+ numeroMaglia + ", dataNascita=" + dataNascita + ", squadra=" + squadra + "]";
	}
}
