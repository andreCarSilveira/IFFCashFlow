package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import modelo.Produto;
import service.ProdutoService;


@ViewScoped
@ManagedBean
public class PrecificacaoBean {

	private String filtro;
	private Boolean editando = false;
	private Boolean gravar = true; 
	
	@EJB
	private ProdutoService produtoService;
	
	private Produto produto = new Produto();
	private List<Produto> produtos = new ArrayList<Produto>();
	
	public PrecificacaoBean() {
    	produto = new Produto(); 
    }
	
	@PostConstruct 
	private void inicializar() {
		listarProdutos();
	}
	
	public void carregarProduto(Produto prod) {
		produto = prod;
		editando = true;
		gravar = false;
	}
	
	public Double calcularPrecoVenda() {
		Double dividendo = produto.getCustoFixoUnitario() + produto.getCustoVariavelUnitario();
		Double divisor = 1 - (produto.getImpostos() + produto.getMargemLucro());
		Double resultado = dividendo / divisor;
		
		return resultado;
	}

	public void excluir(Produto prod) {
		produtoService.remove(prod);
		listarProdutos();
		FacesContext.getCurrentInstance().
		addMessage("msg1", new FacesMessage("Item excluído com sucesso!"));
	}

	public void listarProdutos() {
		produtos = produtoService.listarProdutos(); 
	}

	public void gravar() {
	
		if(produto.getId() != null) {
			produto.setPrecoVenda(calcularPrecoVenda());
			produtoService.merge(produto);
			FacesContext.getCurrentInstance().
			addMessage("msg1", new FacesMessage("Produto atualizado com sucesso!")); 
		} else {
			produto.setPrecoVenda(calcularPrecoVenda());
			produtoService.create(produto);
			FacesContext.getCurrentInstance().
			addMessage("msg1", new FacesMessage("Produto gravado com sucesso!")); 
		}
		
		produto = new Produto();
		
		
		listarProdutos();
		
		gravar = true;
		editando = false;
	}


	public String getFiltro() {
		return filtro;
	}


	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}


	public Boolean getEditando() {
		return editando;
	}


	public void setEditando(Boolean editando) {
		this.editando = editando;
	}


	public Boolean getGravar() {
		return gravar;
	}


	public void setGravar(Boolean gravar) {
		this.gravar = gravar;
	}


	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public List<Produto> getProdutos() {
		return produtos;
	}


	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	
	
	
}
