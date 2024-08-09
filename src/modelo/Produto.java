package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produto {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private Integer previsaoDemanda;
	private Integer vendas;
	private String unidade;
	private Double impostos;
	private Double margemLucro;
	private Double precoVenda;
	private Double custoFixo;
	private Double custoVariavel;
	
	private Double custoFixoUnitario;
	private Double custoVariavelUnitario;

	private Long quantidade;	
	private boolean projecao;
	private Double receita;
	private Double receitaReal;
	
	public Produto() {
		
	}
	
	public Double resultReceita() {
		receita = previsaoDemanda * precoVenda;
		return receita;
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

	public Integer getPrevisaoDemanda() {
		return previsaoDemanda;
	}

	public void setPrevisaoDemanda(Integer previsaoDemanda) {
		this.previsaoDemanda = previsaoDemanda;
	}

	public Double getImpostos() {
		return impostos;
	}

	public void setImpostos(Double impostos) {
		this.impostos = impostos;
	}

	public Double getMargemLucro() {
		return margemLucro;
	}

	public void setMargemLucro(Double margemLucro) {
		this.margemLucro = margemLucro;
	}

	public Double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public boolean isProjecao() {
		return projecao;
	}

	public void setProjecao(boolean projecao) {
		this.projecao = projecao;
	}

	public Double getReceita() {
		return receita;
	}

	public void setReceita(Double receita) {
		this.receita = receita;
	}

	public Integer getVendas() {
		return vendas;
	}

	public void setVendas(Integer vendas) {
		this.vendas = vendas;
	}

	public Double getReceitaReal() {
		return receitaReal;
	}

	public void setReceitaReal(Double receitaReal) {
		this.receitaReal = receitaReal;
	}

	public Double getCustoFixo() {
		return custoFixo;
	}

	public void setCustoFixo(Double custoFixo) {
		this.custoFixo = custoFixo;
	}

	public Double getCustoVariavel() {
		return custoVariavel;
	}

	public void setCustoVariavel(Double custoVariavel) {
		this.custoVariavel = custoVariavel;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Double getCustoFixoUnitario() {
		return custoFixoUnitario;
	}

	public void setCustoFixoUnitario(Double custoFixoUnitario) {
		this.custoFixoUnitario = custoFixoUnitario;
	}

	public Double getCustoVariavelUnitario() {
		return custoVariavelUnitario;
	}

	public void setCustoVariavelUnitario(Double custoVariavelUnitario) {
		this.custoVariavelUnitario = custoVariavelUnitario;
	}
	
		
}
