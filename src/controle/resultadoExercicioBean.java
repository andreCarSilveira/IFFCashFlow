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

import modelo.DespesaReceita;
import modelo.FluxoDeCaixa;
import modelo.Lucro;
import modelo.Produto;
import service.FluxoDeCaixaService;
import service.ProdutoService;

@ViewScoped
@ManagedBean
public class resultadoExercicioBean {
	private FluxoDeCaixa rateio = new FluxoDeCaixa();

	private DespesaReceita despRec;
	private List<DespesaReceita> tiposDespRec;

	private List<FluxoDeCaixa> listDRbruto = new ArrayList<FluxoDeCaixa>();
	private List<FluxoDeCaixa> listDRoperacional = new ArrayList<FluxoDeCaixa>();
	private List<FluxoDeCaixa> listDRliquido = new ArrayList<FluxoDeCaixa>();

	private Double lucroBruto = 0.0;
	private Double lucroOperacional = 0.0;
	private Double lucroLiquido = 0.0;

	private Long idProduto = 0L;
	private List<Produto> produtos = new ArrayList<Produto>();

	private Lucro lucro;
	private List<Lucro> tiposLucro;

	private Integer somaVendas = 0;
	private Double somaPreco = 0.0;
	private Double somaReceita = 0.0;
	private Double precoMedio = 0.0;

	private Integer vendas = 0;

	private Double receita = 0.0;

	private String descricaoAux;
	
	private Integer qntdProd = 0;

	@EJB
	private FluxoDeCaixaService rateioService;
	
	@EJB
	private ProdutoService produtoService;

	public void calculoLucros() {
		lucroBruto = somaReceita - (rateioService.obterSomaDespesasBruto(true) - rateioService.obterSomaReceitasBruto(true));

		lucroOperacional = lucroBruto
				- (rateioService.obterSomaDespesasOperacional(true) - rateioService.obterSomaReceitasOperacional(true));

		lucroLiquido = lucroOperacional
				- (rateioService.obterSomaDespesasLiquido(true) - rateioService.obterSomaReceitasLiquido(true));
	}

	public void resultReceita(Produto prod) {

		boolean hasVendasNulas = false;

		for (Produto produto : produtos) {
			if (produto.getVendas() == null) {
				hasVendasNulas = true;
				break;
			}
		}

		if (!hasVendasNulas) {
			receita = prod.getVendas() * prod.getPrecoVenda();
			System.out.println(receita);

			prod.setReceitaReal(receita);
			produtoService.merge(prod);
		} else {
			FacesContext.getCurrentInstance().addMessage("msg1",
					new FacesMessage("Receita não será calculada ainda. Há produtos com 'vendas' zeradas."));
		}

		prod = new Produto();
		receita = 0.0;
	}
	
	public void excluir(Long id) {
		FluxoDeCaixa fluxo = rateioService.obtemPorId(id);
		rateioService.remove(fluxo);
		atualizarLista();
		//FacesContext.getCurrentInstance().
		//addMessage("msg1", new FacesMessage("Item excluído com sucesso!"));
	}

	public void calculoVendas() {
		// Verifica se há algum produto com o campo "vendas" nulo
		boolean hasVendasNulas = false;

		if(!produtos.isEmpty()) {
			for (Produto produto : produtos) {
				if (produto.getVendas() == null) {
					hasVendasNulas = true;
					break;
				}
			}

		
		
		
		// Se não houver vendas nulas, executa o cálculo
		if (!hasVendasNulas) {
			somaVendas = rateioService.calcularSomaVendas();
			somaPreco = rateioService.calcularSomaPreco();
			somaReceita = rateioService.calcularSomaReceitaReal();

			if (somaVendas == 0) {
				FacesContext.getCurrentInstance().addMessage("msg1", new FacesMessage(
						"Preço médio não será calculado ainda. Todos os produtos estão com as 'vendas' zeradas."));
				precoMedio = 0.0;
			}
			qntdProd = rateioService.contarProdutos(produtos);
			precoMedio = somaPreco / qntdProd;
		}
		}
//		somaVendas = rateioService.calcularSomaVendas();
//		somaPreco = rateioService.calcularSomaPreco();
//		somaReceita = rateioService.calcularSomaReceita();
//
//		precoMedio = somaReceita / somaVendas;
	}

	public void mergeProd() {
		if (idProduto == 0L) {
			FacesContext.getCurrentInstance().addMessage("msg1",
					new FacesMessage("Selecione um produto válido ou insira a quantidade de vendas"));
		} else {
			Produto p = produtoService.obtemPorId(idProduto);
			System.out.println(p);
			p.setVendas(vendas);
			System.out.println(p.getVendas());
			produtoService.merge(p);

			produtos = produtoService.listAll();
			resultReceita(p);
			calculoVendas();

			p = new Produto();
			vendas = 0;
		}
	}

	public void gravar() {
		rateio.setResultado(true);
		rateio.setDespRec(despRec);

		if (despRec == DespesaReceita.DESPESA_OR_RECEITA) {
			FacesContext.getCurrentInstance().addMessage("msg1",
					new FacesMessage("Campo Despesa ou Receita obrigatório!"));

		} else if (lucro == Lucro.TIPO_LUCRO) {
			FacesContext.getCurrentInstance().addMessage("msg1", new FacesMessage("Campo Tipo do lucro obrigatório!"));

		} else if (rateio.getDespRec() == DespesaReceita.DESPESA) {
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
		listDRbruto = rateioService.obterRateiosBrutoOrdenados(true);
		listDRoperacional = rateioService.obterRateiosOperacionalOrdenados(true);
		listDRliquido = rateioService.obterRateiosLiquidoOrdenados(true);

		calculoLucros();
	}

	@PostConstruct
	private void inicializar() {
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

	public Integer getSomaVendas() {
		return somaVendas;
	}

	public void setSomaVendas(Integer somaVendas) {
		this.somaVendas = somaVendas;
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

	public Integer getVendas() {
		return vendas;
	}

	public void setVendas(Integer vendas) {
		this.vendas = vendas;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Integer getQntdProd() {
		return qntdProd;
	}

	public void setQntdProd(Integer qntdProd) {
		this.qntdProd = qntdProd;
	}
}
