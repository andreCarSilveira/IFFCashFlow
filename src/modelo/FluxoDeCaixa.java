package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FluxoDeCaixa {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	private DespesaReceita despRec;
	private Double valor;
	private Lucro lucro;
	private Boolean resultado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public DespesaReceita getDespRec() {
		return despRec;
	}
	public void setDespRec(DespesaReceita despRec) {
		this.despRec = despRec;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Lucro getLucro() {
		return lucro;
	}
	public void setLucro(Lucro lucro) {
		this.lucro = lucro;
	}
	public Boolean getResultado() {
		return resultado;
	}
	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}
	
}
