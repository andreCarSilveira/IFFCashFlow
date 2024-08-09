package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Empresa {
	
	@Id @GeneratedValue
	private Long id;
	
	private String nome;
	private Double capitalSocial;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Double getCapitalSocial() {
		return capitalSocial;
	}
	
	public void setCapitalSocial(Double capitalSocial) {
		this.capitalSocial = capitalSocial;
	}
	
}