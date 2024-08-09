package modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Estoque {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String produto;
	private Date hora;
	private Double quantidade;
	private Double custoUnidade;
	private Double saldo;
	
	
	
	public Estoque() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getCustoUnidade() {
		return custoUnidade;
	}

	public void setCustoUnidade(Double custoUnidade) {
		this.custoUnidade = custoUnidade;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Double getSaldo() {
		// Calcular o saldo multiplicando quantidade por custoUnidade
		if (quantidade != null && custoUnidade != null) {
			saldo = quantidade * custoUnidade;
		} else {
			saldo = 0.0; // ou outra ação que faça sentido para o seu contexto
		}
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
}
