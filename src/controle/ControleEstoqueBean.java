package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import modelo.Estoque;
import modelo.Produto;
import service.EstoqueService;
import service.ProdutoService;

@ViewScoped
@ManagedBean
public class ControleEstoqueBean {

	private Estoque estoque = new Estoque();
	
	private Boolean editando = false;
	private Boolean gravar = true;
	private Long idProduto = 0L;
	private Produto produto;
	
	private List<Produto> produtos = new ArrayList<Produto>();
	
	
	private List<Estoque> estoques = new ArrayList<Estoque>();
	
	@EJB
	private EstoqueService estoqueService;
	
	@EJB
	private ProdutoService produtoService;
	
	
	
	public ControleEstoqueBean() {
		estoque = new Estoque();
	}
	
	@PostConstruct 
	private void inicializar() {
		listarEstoque();
		atualizarLista();
		produtos = produtoService.listAll();
	}
	
	public void carregarEstoque(Estoque est) {
		estoque = est;
		editando = true;
		gravar = false;
	}
	
	public void excluir(Estoque est) {
		estoqueService.remove(est);
		listarEstoque();
		FacesContext.getCurrentInstance().
		addMessage("msg1", new FacesMessage("Item excluído com sucesso!"));
	}
	
	private void atualizarLista() {
		estoques = estoqueService.listAll();
	}
	
	public void listarEstoque() {
		estoques = estoqueService.listarEstoques(); 
	}
	
	public Double calcularSomaSaldo() {
        List<Estoque> estoques = getEstoques(); // Substitua pelo método que obtém a lista de estoques do seu modelo

        double somaSaldo = 0.0;

        for (Estoque estoque : estoques) {
            if (estoque.getSaldo() != null) {
                somaSaldo += estoque.getSaldo();
            }
        }

        return somaSaldo;
    }


	public void gravar() {
		if (editando) {
			estoqueService.merge(estoque);
		} else {
			estoqueService.create(estoque);
		}
		atualizarLista();
		estoque = new Estoque();
		gravar = true;
		editando = false;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
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

	public List<Estoque> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<Estoque> estoques) {
		this.estoques = estoques;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
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
