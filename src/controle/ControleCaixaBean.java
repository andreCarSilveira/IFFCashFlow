package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Caixa;
import modelo.Estoque;
import service.CaixaService;

@ViewScoped
@ManagedBean
public class ControleCaixaBean {

	private String filtro;
	private Caixa caixa = new Caixa();
	
	private Boolean editando = false;
	private Boolean gravar = true;
	
	private List<Caixa> caixas = new ArrayList<Caixa>();
	
	@EJB
	private CaixaService caixaService;
	
	public ControleCaixaBean() {
		caixa = new Caixa();
	}
	
	@PostConstruct 
	private void inicializar() {
		listarCaixa();
		atualizarLista();
	}
	
	public void carregarCaixa(Caixa cax) {
		caixa = cax;
		editando = true;
		gravar = false;
	}
	
	public void excluir(Caixa cax) {
		caixaService.remove(cax);
		listarCaixa();
		FacesContext.getCurrentInstance().
		addMessage("msg1", new FacesMessage("Item excluído com sucesso!"));
	}
	
	private void atualizarLista() {
		caixas = caixaService.listAll();
	}
	
	public void listarCaixa() {
		caixas = caixaService.listarCaixas(); 
	}
	
	public Double calcularSomaSaldoCaixa() {
        List<Caixa> caixas = getCaixas(); // Substitua pelo método que obtém a lista de estoques do seu modelo

        double somaSaldoCaixa = 0.0;

        for (Caixa caixa : caixas) {
            if (caixa.getSaldo() != null) {
            	somaSaldoCaixa += caixa.getSaldo();
            }
        }

        return somaSaldoCaixa;
    }

	public void gravar() {
		if (editando) {
			caixaService.merge(caixa);
		} else {
			caixaService.create(caixa);
		}
		atualizarLista();
		caixa = new Caixa();
		gravar = true;
		editando = false;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
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

	public List<Caixa> getCaixas() {
		return caixas;
	}

	public void setCaixas(List<Caixa> caixas) {
		this.caixas = caixas;
	}

	public CaixaService getCaixaService() {
		return caixaService;
	}

	public void setCaixaService(CaixaService caixaService) {
		this.caixaService = caixaService;
	}
	
	
	
}
