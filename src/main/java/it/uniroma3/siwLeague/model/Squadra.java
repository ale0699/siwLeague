package it.uniroma3.siwLeague.model;


import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Squadra {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idSquadra;
	@NotBlank
	private String nome;
	private String logo;
	private int punti = 0;
	private int vittorie = 0;
	private int pareggi = 0;
	private int sconfitte = 0;
	private int golFatti = 0;
	private int golSubiti = 0;
	
	@ManyToOne
	private GestoreSquadra teamManager;
	
	@ManyToOne
	private Torneo torneo;
    
	@OneToMany(mappedBy = "squadraCasa")
	private List<Partita> partiteCasa;
	
	@OneToMany(mappedBy = "squadraFuoriCasa")
	private List<Partita> partiteFuoriCasa;
	
	@OneToMany(mappedBy = "squadra", cascade = CascadeType.REMOVE)
	private List<Giocatore> giocatori;
	
	public void setVittoria(int golSegnati, int golSubiti) {
		
		this.punti += 3;
		this.vittorie += 1;
		this.golFatti += golSegnati;
		this.golSubiti += golSubiti;
	}
	
	public void setPareggio(int gol) {
		
		this.punti += 1;
		this.pareggi += 1;
		this.golFatti += gol;
		this.golSubiti += gol;
	}
	
	public void setSconfitta(int golSegnati, int golSubiti) {
		
		this.sconfitte += 1;
		this.golFatti += golSegnati;
		this.golSubiti += golSubiti;
	}
	
	public void removeVittoria(int golSegnati, int golSubiti) {
		
		this.punti -= 3;
		this.vittorie -= 1;
		this.golFatti -= golSegnati;
		this.golSubiti -= golSubiti;
	}
	
	public void removePareggio(int gol) {
		
		this.punti -= 1;
		this.pareggi -= 1;
		this.golFatti -= gol;
		this.golSubiti -= gol;
	}
	
	public void removeSconfitta(int golSegnati, int golSubiti) {
		
		this.sconfitte -= 1;
		this.golFatti -= golSegnati;
		this.golSubiti -= golSubiti;
	}
	
	public int partiteGiocate() {
		
		return this.partiteCasa.size() + this.partiteFuoriCasa.size();
	}

	public Long getIdSquadra() {
		return idSquadra;
	}

	public void setIdSquadra(Long idSquadra) {
		this.idSquadra = idSquadra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public GestoreSquadra getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(GestoreSquadra teamManager) {
		this.teamManager = teamManager;
	}

	public Torneo getTorneo() {
		return torneo;
	}

	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}

	public List<Partita> getPartiteCasa() {
		return partiteCasa;
	}

	public void setPartiteCasa(List<Partita> partiteCasa) {
		this.partiteCasa = partiteCasa;
	}

	public List<Partita> getPartiteFuoriCasa() {
		return partiteFuoriCasa;
	}

	public void setPartiteFuoriCasa(List<Partita> partiteFuoriCasa) {
		this.partiteFuoriCasa = partiteFuoriCasa;
	}

	public List<Giocatore> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(List<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}
	
	public int getPunti() {
		return punti;
	}

	public void setPunti(int punti) {
		this.punti = punti;
	}

	public int getVittorie() {
		return vittorie;
	}

	public void setVittorie(int vittorie) {
		this.vittorie = vittorie;
	}

	public int getPareggi() {
		return pareggi;
	}

	public void setPareggi(int pareggi) {
		this.pareggi = pareggi;
	}

	public int getSconfitte() {
		return sconfitte;
	}

	public void setSconfitte(int sconfitte) {
		this.sconfitte = sconfitte;
	}

	public int getGolFatti() {
		return golFatti;
	}

	public void setGolFatti(int golFatti) {
		this.golFatti = golFatti;
	}

	public int getGolSubiti() {
		return golSubiti;
	}

	public void setGolSubiti(int golSubiti) {
		this.golSubiti = golSubiti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(giocatori, golFatti, golSubiti, idSquadra, logo, nome, pareggi, partiteCasa,
				partiteFuoriCasa, punti, sconfitte, teamManager, torneo, vittorie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(giocatori, other.giocatori) && golFatti == other.golFatti && golSubiti == other.golSubiti
				&& Objects.equals(idSquadra, other.idSquadra) && Objects.equals(logo, other.logo)
				&& Objects.equals(nome, other.nome) && pareggi == other.pareggi
				&& Objects.equals(partiteCasa, other.partiteCasa)
				&& Objects.equals(partiteFuoriCasa, other.partiteFuoriCasa) && punti == other.punti
				&& sconfitte == other.sconfitte && Objects.equals(teamManager, other.teamManager)
				&& Objects.equals(torneo, other.torneo) && vittorie == other.vittorie;
	}

	@Override
	public String toString() {
		return "Squadra [idSquadra=" + idSquadra + ", nome=" + nome + ", logo=" + logo + ", punti=" + punti
				+ ", vittorie=" + vittorie + ", pareggi=" + pareggi + ", sconfitte=" + sconfitte + ", golFatti="
				+ golFatti + ", golSubiti=" + golSubiti + ", teamManager=" + teamManager + ", torneo=" + torneo
				+ ", partiteCasa=" + partiteCasa + ", partiteFuoriCasa=" + partiteFuoriCasa + ", giocatori=" + giocatori
				+ "]";
	}
}
