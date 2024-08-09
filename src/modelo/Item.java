package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String unidadeMedia;
	private Double custo;
	private Double quantidade;
	private Double custoTotal;
	
	public Item() {
		
	}

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

	public String getUnidadeMedia() {
		return unidadeMedia;
	}

	public void setUnidadeMedia(String unidadeMedia) {
		this.unidadeMedia = unidadeMedia;
	}

	public Double getCusto() {
		return custo;
	}

	public void setCusto(Double custo) {
		this.custo = custo;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getCustoTotal() {
		// Calcular o saldo multiplicando quantidade por custoUnidade
				if (quantidade != null && custo != null) {
					custoTotal = quantidade * custo;
				} else {
					custoTotal = 0.0; 
				}
				return custoTotal;
	}

	public void setCustoTotal(Double custoTotal) {
		this.custoTotal = custoTotal;
	}
	
	
}
