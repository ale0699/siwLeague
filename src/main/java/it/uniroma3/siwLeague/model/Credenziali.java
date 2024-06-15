package it.uniroma3.siwLeague.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Credenziali {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCredenziali;
	private String username;
	private String password;
	
	public Long getIdCredenziali() {
		return idCredenziali;
	}
	public void setIdCredenziali(Long idCredenziali) {
		this.idCredenziali = idCredenziali;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int hashCode() {
		return Objects.hash(idCredenziali, password, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credenziali other = (Credenziali) obj;
		return Objects.equals(idCredenziali, other.idCredenziali) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "Credenziali [idCredenziali=" + idCredenziali + ", username=" + username + ", password=" + password
				+ "]";
	}
	
	
}
