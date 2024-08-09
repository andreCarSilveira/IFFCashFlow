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
import modelo.Item;
import service.ItemService;

@ViewScoped
@ManagedBean
public class OrcamentoGastosBean {

	private Item item = new Item();
	
	private Boolean editando = false;
	private Boolean gravar = true;
	
	private List<Item> itens = new ArrayList<Item>();
	
	
	
	@EJB
	private ItemService itemService;
	
	public OrcamentoGastosBean() {
		item = new Item();
	}
	
	@PostConstruct 
	public void inicializar() {
		listarGastos();
		atualizarLista();
	}
	
	public void carregarItem(Item itm) {
		item = itm;
		editando = true;
		gravar = false;
	}
	
	public void excluir(Item itm) {
		itemService.remove(itm);
		listarGastos();
		FacesContext.getCurrentInstance().
		addMessage("msg1", new FacesMessage("Item excluído com sucesso!"));
	}
	
	private void atualizarLista() {
		itens = itemService.listAll();
	}
	
	public void listarGastos() {
		itens = itemService.listarItens(); 
	}
	
	
	public Double calcularSomaGasto() {
        List<Item> itens = getItens(); // Substitua pelo método que obtém a lista de estoques do seu modelo

        double somaGasto = 0.0;

        for (Item item : itens) {
            if (item.getCustoTotal() != null) {
            	somaGasto += item.getCustoTotal();
            }
        }

        return somaGasto;
    }

	public void gravar() {
		if (editando) {
			itemService.merge(item);
		} else {
			itemService.create(item);
		}
		atualizarLista();
		item = new Item();
		gravar = true;
		editando = false;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	
	
	
	
	
}
