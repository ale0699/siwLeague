package it.uniroma3.siwLeague.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Partita {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPartita;
	private int numeroGiornata;
	private LocalDateTime dataEOra;
	@NotBlank
	private String campo;
	private int golSquadraCasa;
	private int golSquadraFuoriCasa;
	
	@ManyToOne
	private Torneo torneo;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Squadra squadraCasa;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Squadra squadraFuoriCasa;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@MapKeyColumn(name="minuto_gol")
	private Map<Integer ,Giocatore> marcatori;
	
	public Squadra getSquadraVincente() {
		
		if(this.golSquadraCasa>this.golSquadraFuoriCasa) {
			
			return this.squadraCasa;
		}
		else if(this.golSquadraCasa<this.golSquadraFuoriCasa){
			
			return this.squadraFuoriCasa;
		}
		
		return null; //pareggio
	}

	public Long getIdPartita() {
		return idPartita;
	}

	public void setIdPartita(Long idPartita) {
		this.idPartita = idPartita;
	}

	public int getNumeroGiornata() {
		return numeroGiornata;
	}

	public void setNumeroGiornata(int numeroGiornata) {
		this.numeroGiornata = numeroGiornata;
	}

	public LocalDateTime getDataEOra() {
		return dataEOra;
	}

	public void setDataEOra(LocalDateTime dataEOra) {
		this.dataEOra = dataEOra;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public int getGolSquadraCasa() {
		return golSquadraCasa;
	}

	public void setGolSquadraCasa(int golSquadraCasa) {
		this.golSquadraCasa = golSquadraCasa;
	}

	public int getGolSquadraFuoriCasa() {
		return golSquadraFuoriCasa;
	}

	public void setGolSquadraFuoriCasa(int golSquadraFuoriCasa) {
		this.golSquadraFuoriCasa = golSquadraFuoriCasa;
	}

	public Torneo getTorneo() {
		return torneo;
	}

	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}

	public Squadra getSquadraCasa() {
		return squadraCasa;
	}

	public void setSquadraCasa(Squadra squadraCasa) {
		this.squadraCasa = squadraCasa;
	}

	public Squadra getSquadraFuoriCasa() {
		return squadraFuoriCasa;
	}

	public void setSquadraFuoriCasa(Squadra squadraFuoriCasa) {
		this.squadraFuoriCasa = squadraFuoriCasa;
	}

	public Map<Integer, Giocatore> getMarcatori() {
		return marcatori;
	}

	public void setMarcatori(Map<Integer, Giocatore> marcatori) {
		this.marcatori = marcatori;
	}

	@Override
	public int hashCode() {
		return Objects.hash(campo, dataEOra, golSquadraCasa, golSquadraFuoriCasa, idPartita, marcatori, numeroGiornata,
				squadraCasa, squadraFuoriCasa, torneo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partita other = (Partita) obj;
		return Objects.equals(campo, other.campo) && Objects.equals(dataEOra, other.dataEOra)
				&& golSquadraCasa == other.golSquadraCasa && golSquadraFuoriCasa == other.golSquadraFuoriCasa
				&& Objects.equals(idPartita, other.idPartita) && Objects.equals(marcatori, other.marcatori)
				&& numeroGiornata == other.numeroGiornata && Objects.equals(squadraCasa, other.squadraCasa)
				&& Objects.equals(squadraFuoriCasa, other.squadraFuoriCasa) && Objects.equals(torneo, other.torneo);
	}

	@Override
	public String toString() {
		return "Partita [idPartita=" + idPartita + ", numeroGiornata=" + numeroGiornata + ", dataEOra=" + dataEOra
				+ ", campo=" + campo + ", golSquadraCasa=" + golSquadraCasa + ", golSquadraFuoriCasa="
				+ golSquadraFuoriCasa + ", torneo=" + torneo + ", squadraCasa=" + squadraCasa + ", squadraFuoriCasa="
				+ squadraFuoriCasa + ", marcatori=" + marcatori + "]";
	}
}
