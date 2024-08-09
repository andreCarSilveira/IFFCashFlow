package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Socio {
	
	@Id @GeneratedValue
	private Long id;

	private String nome;
	private Double capital;
	private Double part;
	
	@ManyToOne
	private Empresa empresa;

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

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public Double getPart() {
	    if (empresa != null && capital != null && empresa.getCapitalSocial() != 0) {
	        return (capital / empresa.getCapitalSocial()) * 100;
	    } else {
	        return 5.0;
	    }
	}

	public void setPart(Double part) {
		this.part = part;
	}
}