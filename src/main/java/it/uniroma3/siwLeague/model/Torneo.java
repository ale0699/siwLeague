package it.uniroma3.siwLeague.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Torneo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idTorneo;
	@NotBlank
	private String nome;
	private String descrizione;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private int numeroMassimoSquadrePartecipanti;
	private int numeroMassimoGiocatoriIscrivibili;
	private int montepremi;
	private boolean iscrizioneInCorso;
	private boolean torneoInCorso;
	
	@OneToMany(mappedBy = "torneo")
	private List<Squadra> squadreIscritte;
	
	@OneToMany(mappedBy = "torneo")
	private List<Partita> partiteTorneo;

	public Long getIdTorneo() {
		return idTorneo;
	}

	public void setIdTorneo(Long idTorneo) {
		this.idTorneo = idTorneo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	public int getNumeroMassimoSquadrePartecipanti() {
		return numeroMassimoSquadrePartecipanti;
	}

	public void setNumeroMassimoSquadrePartecipanti(int numeroSquadrePartecipanti) {
		this.numeroMassimoSquadrePartecipanti = numeroSquadrePartecipanti;
	}

	public int getNumeroMassimoGiocatoriIscrivibili() {
		return numeroMassimoGiocatoriIscrivibili;
	}

	public void setNumeroMassimoGiocatoriIscrivibili(int numeroMassimoGiocatoriIscrivibili) {
		this.numeroMassimoGiocatoriIscrivibili = numeroMassimoGiocatoriIscrivibili;
	}

	public int getMontepremi() {
		return montepremi;
	}

	public void setMontepremi(int montepremi) {
		this.montepremi = montepremi;
	}

	public boolean isIscrizioneInCorso() {
		return iscrizioneInCorso;
	}

	public void setIscrizioneInCorso(boolean inCorso) {
		this.iscrizioneInCorso = inCorso;
	}

	public boolean isTorneoInCorso() {
		return torneoInCorso;
	}

	public void setTorneoInCorso(boolean torneoInCorso) {
		this.torneoInCorso = torneoInCorso;
	}

	public List<Squadra> getSquadreIscritte() {
		return squadreIscritte;
	}

	public void setSquadreIscritte(List<Squadra> squadreIscritte) {
		this.squadreIscritte = squadreIscritte;
	}

	public List<Partita> getPartiteTorneo() {
		return partiteTorneo;
	}

	public void setPartiteTorneo(List<Partita> partiteTorneo) {
		this.partiteTorneo = partiteTorneo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataFine, dataInizio, descrizione, idTorneo, iscrizioneInCorso, montepremi, nome,
				numeroMassimoGiocatoriIscrivibili, numeroMassimoSquadrePartecipanti, partiteTorneo, squadreIscritte,
				torneoInCorso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Torneo other = (Torneo) obj;
		return Objects.equals(dataFine, other.dataFine) && Objects.equals(dataInizio, other.dataInizio)
				&& Objects.equals(descrizione, other.descrizione) && Objects.equals(idTorneo, other.idTorneo)
				&& iscrizioneInCorso == other.iscrizioneInCorso && montepremi == other.montepremi
				&& Objects.equals(nome, other.nome)
				&& numeroMassimoGiocatoriIscrivibili == other.numeroMassimoGiocatoriIscrivibili
				&& numeroMassimoSquadrePartecipanti == other.numeroMassimoSquadrePartecipanti
				&& Objects.equals(partiteTorneo, other.partiteTorneo)
				&& Objects.equals(squadreIscritte, other.squadreIscritte) && torneoInCorso == other.torneoInCorso;
	}

	@Override
	public String toString() {
		return "Torneo [idTorneo=" + idTorneo + ", nome=" + nome + ", descrizione=" + descrizione + ", dataInizio="
				+ dataInizio + ", dataFine=" + dataFine + ", numeroMassimoSquadrePartecipanti="
				+ numeroMassimoSquadrePartecipanti + ", numeroMassimoGiocatoriIscrivibili="
				+ numeroMassimoGiocatoriIscrivibili + ", montepremi=" + montepremi + ", iscrizioneInCorso="
				+ iscrizioneInCorso + ", torneoInCorso=" + torneoInCorso + ", squadreIscritte=" + squadreIscritte
				+ ", partiteTorneo=" + partiteTorneo + "]";
	}

}
