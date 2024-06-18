package it.uniroma3.siwLeague.model;

import java.util.List;
import java.util.Objects;

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
	
	@ManyToOne
	private GestoreSquadra teamManager;
	
	@ManyToOne
	private Torneo torneo;
	
	@OneToMany(mappedBy = "squadraCasa")
	private List<Partita> partiteCasa;
	
	@OneToMany(mappedBy = "squadraFuoriCasa")
	private List<Partita> partiteFuoriCasa;
	
	@OneToMany(mappedBy = "squadra")
	private List<Giocatore> giocatori;

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
	
	public int calcolaPosizione() {
	    // Implementa la logica per calcolare la posizione
	    return 0; // Placeholder, implementa la logica reale
	}
	
	public int calcolaPartiteGiocate() {
	    return partiteCasa.size() + partiteFuoriCasa.size();
	}
	
	private int calcolaVittorie() {
	    int vittorie = 0;
	    for (Partita partita : partiteCasa) {
	        if (partita.getSquadraVincente().equals(this)) {
	            vittorie++;
	        }
	    }
	    for (Partita partita : partiteFuoriCasa) {
	        if (partita.getSquadraVincente().equals(this)) {
	            vittorie++;
	        }
	    }
	    return vittorie;
	}

	private int calcolaPareggi() {
	    int pareggi = 0;
	    for (Partita partita : partiteCasa) {
	        if (partita.isPareggio()) {
	            pareggi++;
	        }
	    }
	    for (Partita partita : partiteFuoriCasa) {
	        if (partita.isPareggio()) {
	            pareggi++;
	        }
	    }
	    return pareggi;
	}

	private int calcolaSconfitte() {
	    int sconfitte = 0;
	    for (Partita partita : partiteCasa) {
	        if (!partita.getSquadraVincente().equals(this)) {
	            sconfitte++;
	        }
	    }
	    for (Partita partita : partiteFuoriCasa) {
	        if (!partita.getSquadraVincente().equals(this)) {
	            sconfitte++;
	        }
	    }
	    return sconfitte;
	}

	private int calcolaGolFatti() {
	    int golFatti = 0;
	    for (Partita partita : partiteCasa) {
	        golFatti += partita.getGolSquadraCasa();
	    }
	    for (Partita partita : partiteFuoriCasa) {
	        golFatti += partita.getGolSquadraFuoriCasa();
	    }
	    return golFatti;
	}

	private int calcolaGolSubiti() {
	    int golSubiti = 0;
	    for (Partita partita : partiteCasa) {
	        golSubiti += partita.getGolSquadraFuoriCasa();
	    }
	    for (Partita partita : partiteFuoriCasa) {
	        golSubiti += partita.getGolSquadraCasa();
	    }
	    return golSubiti;
	}

	private int calcolaPunti() {
	    return (this.calcolaVittorie() * 3) + this.calcolaPareggi();
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSquadra, logo, nome, partiteCasa, partiteFuoriCasa, teamManager, torneo);
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
		return Objects.equals(idSquadra, other.idSquadra) && Objects.equals(logo, other.logo)
				&& Objects.equals(nome, other.nome) && Objects.equals(partiteCasa, other.partiteCasa)
				&& Objects.equals(partiteFuoriCasa, other.partiteFuoriCasa)
				&& Objects.equals(teamManager, other.teamManager) && Objects.equals(torneo, other.torneo);
	}

	@Override
	public String toString() {
		return "Squadra [idSquadra=" + idSquadra + ", nome=" + nome + ", logo=" + logo + ", teamManager=" + teamManager
				+ ", torneo=" + torneo + ", partiteCasa=" + partiteCasa + ", partiteFuoriCasa=" + partiteFuoriCasa
				+ "]";
	}
	
	
}
