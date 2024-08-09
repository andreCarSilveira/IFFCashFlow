package controle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Caixa;
import modelo.DespesaReceita;
import modelo.Lucro;
import modelo.Produto;
import modelo.FluxoDeCaixa;
import service.ProdutoService;
import service.FluxoDeCaixaService;

@ViewScoped
@ManagedBean
public class ProjecaoVendasBean {
	private FluxoDeCaixa rateio = new FluxoDeCaixa();
	
	private DespesaReceita despRec;
	private List<DespesaReceita> tiposDespRec;
	
	@EJB
	private FluxoDeCaixaService fluxoDeCaixaService;
	
	private List<FluxoDeCaixa> listDRbruto = new ArrayList<FluxoDeCaixa>();
	private List<FluxoDeCaixa> listDRoperacional = new ArrayList<FluxoDeCaixa>();
	private List<FluxoDeCaixa> listDRliquido = new ArrayList<FluxoDeCaixa>();
	
	private Double lucroBruto = 0.0;
	private Double lucroOperacional = 0.0;
	private Double lucroLiquido = 0.0;	

	private List<Produto> produtos = new ArrayList<Produto>();
	
	private Lucro lucro;
	private List<Lucro> tiposLucro;
	
	private Integer somaDemanda = 0;
	private Double somaPreco = 0.0;
	private Double somaReceita = 0.0;
	private Double precoMedio = 0.0;
	
	private Double receita = 0.0;
	
	private String descricaoAux;
	
	@EJB
	private FluxoDeCaixaService rateioService;
	
	@EJB
	private ProdutoService produtoService;
	
	public void calculoLucros() {
		Double somaDespesasBruto = rateioService.obterSomaDespesasBruto(false);
		Double somaReceitasBruto = rateioService.obterSomaReceitasBruto(false);
		Double somaDespesasOperacional = rateioService.obterSomaDespesasOperacional(false);
	    Double somaReceitasOperacional = rateioService.obterSomaReceitasOperacional(false);
	    Double somaDespesasLiquido = rateioService.obterSomaDespesasLiquido(false);
	    Double somaReceitasLiquido = rateioService.obterSomaReceitasLiquido(false);

	    if (somaDespesasBruto != null && somaReceitasBruto != null &&
	        somaDespesasOperacional != null && somaReceitasOperacional != null &&
	        somaDespesasLiquido != null && somaReceitasLiquido != null &&
	        somaReceita != null) {

	        lucroBruto = somaReceita - (somaDespesasBruto - somaReceitasBruto);
	        lucroOperacional = lucroBruto - (somaDespesasOperacional - somaReceitasOperacional);
	        lucroLiquido = lucroOperacional - (somaDespesasLiquido - somaReceitasLiquido);
	    } else if(somaDespesasBruto != null && somaReceitasBruto != null &&
		        somaDespesasOperacional != null && somaReceitasOperacional != null &&
		        somaDespesasLiquido != null && somaReceitasLiquido != null &&
		        somaReceita == null) {
	    	
	    	lucroBruto = 0 - (somaDespesasBruto - somaReceitasBruto);
	        lucroOperacional = lucroBruto - (somaDespesasOperacional - somaReceitasOperacional);
	        lucroLiquido = lucroOperacional - (somaDespesasLiquido - somaReceitasLiquido);
	    }
	}
	
	public void excluir(Long id) {
		FluxoDeCaixa fluxo = fluxoDeCaixaService.obtemPorId(id);
		fluxoDeCaixaService.remove(fluxo);
		atualizarLista();
		//FacesContext.getCurrentInstance().
		//addMessage("msg1", new FacesMessage("Item excluído com sucesso!"));
	}
	
	public void resultReceita(Produto prod) {
		receita = prod.getPrevisaoDemanda() * prod.getPrecoVenda();
		
		if(prod.getReceita() == null) {
			prod.setReceita(receita);
			produtoService.merge(prod);		
		} else {
			receita = prod.getReceita();
		}
		
		prod = new Produto();
		receita = 0.0;
	}
	
	public void calculoVendas() {
		somaDemanda = rateioService.calcularSomaPrevisaoDemanda();
		somaPreco = rateioService.calcularSomaPreco();
		somaReceita = rateioService.calcularSomaReceita();

		if (somaDemanda != null && somaPreco != null && somaReceita != null) {
			precoMedio = somaReceita / somaDemanda;
		}
	}
	
	public void gravar() {
		rateio.setResultado(false);
		rateio.setDespRec(despRec);
		
		if(despRec == DespesaReceita.DESPESA_OR_RECEITA) {
        	FacesContext.getCurrentInstance().addMessage("msg1", new FacesMessage("Campo Despesa ou Receita obrigatï¿½rio!"));
        	
        } else if (lucro == Lucro.TIPO_LUCRO) {
        	FacesContext.getCurrentInstance().addMessage("msg1", new FacesMessage("Campo Tipo do lucro obrigatï¿½rio!"));
        	
        } else if(rateio.getDespRec() == DespesaReceita.DESPESA) {
			descricaoAux = "(-) " + rateio.getDescricao();
						
			rateio.setDescricao(descricaoAux);
			
			rateio.setDespRec(despRec);
			rateio.setLucro(lucro);
			
			rateioService.create(rateio);
		} else {
			rateio.setDespRec(despRec);
			rateio.setLucro(lucro);
			
			rateioService.create(rateio);
		}
		rateio = new FluxoDeCaixa();
		atualizarLista();
		descricaoAux = "";
		
		despRec = DespesaReceita.DESPESA_OR_RECEITA;
		lucro = Lucro.TIPO_LUCRO;
	}
	
	public void atualizarLista() {
		listDRbruto = rateioService.obterRateiosBrutoOrdenados(false);
		listDRoperacional = rateioService.obterRateiosOperacionalOrdenados(false);
		listDRliquido = rateioService.obterRateiosLiquidoOrdenados(false);
		
		calculoLucros();
	}
	
	public ProjecaoVendasBean() {
		
	}

	@PostConstruct
	public void inicializar() {
		produtos = produtoService.listAll();
		tiposDespRec = Arrays.asList(DespesaReceita.values());
		tiposLucro = Arrays.asList(Lucro.values());
		
		calculoVendas();
		atualizarLista();
	}

	public FluxoDeCaixa getRateio() {
		return rateio;
	}

	public void setRateio(FluxoDeCaixa rateio) {
		this.rateio = rateio;
	}

	public DespesaReceita getDespRec() {
		return despRec;
	}

	public void setDespRec(DespesaReceita despRec) {
		this.despRec = despRec;
	}

	public List<DespesaReceita> getTiposDespRec() {
		return tiposDespRec;
	}

	public void setTiposDespRec(List<DespesaReceita> tiposDespRec) {
		this.tiposDespRec = tiposDespRec;
	}

	public Lucro getLucro() {
		return lucro;
	}

	public void setLucro(Lucro lucro) {
		this.lucro = lucro;
	}

	public List<Lucro> getTiposLucro() {
		return tiposLucro;
	}

	public void setTiposLucro(List<Lucro> tiposLucro) {
		this.tiposLucro = tiposLucro;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Integer getSomaDemanda() {
		return somaDemanda;
	}

	public void setSomaDemanda(Integer somaDemanda) {
		this.somaDemanda = somaDemanda;
	}

	public Double getSomaPreco() {
		return somaPreco;
	}

	public void setSomaPreco(Double somaPreco) {
		this.somaPreco = somaPreco;
	}

	public Double getSomaReceita() {
		return somaReceita;
	}

	public void setSomaReceita(Double somaReceita) {
		this.somaReceita = somaReceita;
	}

	public Double getPrecoMedio() {
		return precoMedio;
	}

	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}

	public Double getReceita() {
		return receita;
	}

	public void setReceita(Double receita) {
		this.receita = receita;
	}

	public Double getLucroBruto() {
		return lucroBruto;
	}

	public void setLucroBruto(Double lucroBruto) {
		this.lucroBruto = lucroBruto;
	}

	public Double getLucroOperacional() {
		return lucroOperacional;
	}

	public void setLucroOperacional(Double lucroOperacional) {
		this.lucroOperacional = lucroOperacional;
	}

	public Double getLucroLiquido() {
		return lucroLiquido;
	}

	public void setLucroLiquido(Double lucroLiquido) {
		this.lucroLiquido = lucroLiquido;
	}

	public List<FluxoDeCaixa> getListDRbruto() {
		return listDRbruto;
	}

	public void setListDRbruto(List<FluxoDeCaixa> listDRbruto) {
		this.listDRbruto = listDRbruto;
	}

	public List<FluxoDeCaixa> getListDRoperacional() {
		return listDRoperacional;
	}

	public void setListDRoperacional(List<FluxoDeCaixa> listDRoperacional) {
		this.listDRoperacional = listDRoperacional;
	}

	public List<FluxoDeCaixa> getListDRliquido() {
		return listDRliquido;
	}

	public void setListDRliquido(List<FluxoDeCaixa> listDRliquido) {
		this.listDRliquido = listDRliquido;
	}

	public String getDescricaoAux() {
		return descricaoAux;
	}

	public void setDescricaoAux(String descricaoAux) {
		this.descricaoAux = descricaoAux;
	}
	
	
}


