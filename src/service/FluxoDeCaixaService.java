package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.DespesaReceita;
import modelo.Estoque;
import modelo.Lucro;
import modelo.Produto;
import modelo.FluxoDeCaixa;

@Stateless
public class FluxoDeCaixaService extends GenericService<FluxoDeCaixa> {

	public FluxoDeCaixaService() {
		super(FluxoDeCaixa.class);
	}

	public int contarProdutos(List<Produto> listaProdutos) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> query = cb.createQuery(Long.class);
        final Root<Produto> root = query.from(Produto.class);

        // Adiciona uma cláusula WHERE para contar produtos na lista
        query.select(cb.count(root))
             .where(root.in(listaProdutos));

        return getEntityManager().createQuery(query).getSingleResult().intValue();
    }
	
	public Integer calcularSomaPrevisaoDemanda() {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
		final Root<Produto> root = query.from(Produto.class); // Alterado para Produto

		query.select(cb.sum(root.<Integer>get("previsaoDemanda"))); // Alterado para Produto.previsaoDemanda

		return getEntityManager().createQuery(query).getSingleResult();
	}

	public Double calcularSomaReceita() {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Double> query = cb.createQuery(Double.class);
		final Root<Produto> root = query.from(Produto.class); // Alterado para Produto

//		query.select(cb.sum(cb.prod(root.<Double>get("precoVenda"), root.<Integer>get("previsaoDemanda"))));
		
		// Multiplicação de dois valores Double (precoVenda e previsaoDemanda)
	    query.select(cb.sum(root.<Double>get("receita")));
	    
		return getEntityManager().createQuery(query).getSingleResult();
	}
	
	public Double calcularSomaPreco() {
	    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	    CriteriaQuery<Double> query = cb.createQuery(Double.class);
	    Root<Produto> root = query.from(Produto.class);

	    query.select(cb.sum(root.get("precoVenda").as(Double.class)));

	    return getEntityManager().createQuery(query).getSingleResult();
	}
	


	public Double obterSomaReceitasBruto(Boolean resultado) {
	    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	    CriteriaQuery<Double> query = cb.createQuery(Double.class);
	    Root<FluxoDeCaixa> root = query.from(FluxoDeCaixa.class);

	    Expression<Double> sumExpression = cb.sum(root.<Double>get("valor"));
	    Expression<Double> coalescedSum = cb.coalesce(sumExpression, 0.0);

	    query.select(coalescedSum)
	         .where(
	                cb.and(
	                        cb.equal(root.get("despRec"), DespesaReceita.RECEITA),
	                        cb.equal(root.get("lucro"), Lucro.BRUTO),
	                        cb.equal(root.get("resultado"), resultado)
	                )
	        );

	    return getEntityManager().createQuery(query).getSingleResult();
	}
	
	public Integer calcularSomaVendas() {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
		final Root<Produto> root = query.from(Produto.class); // Alterado para Produto
		
		query.select(cb.sum(root.<Integer>get("vendas"))); // Alterado para Produto.previsaoDemanda
		
		return getEntityManager().createQuery(query).getSingleResult();
	}

	public Double calcularSomaReceitaReal() {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Double> query = cb.createQuery(Double.class);
		final Root<Produto> root = query.from(Produto.class); // Alterado para Produto

//		query.select(cb.sum(cb.prod(root.<Double>get("precoVenda"), root.<Integer>get("previsaoDemanda"))));
		
		// Multiplicação de dois valores Double (precoVenda e previsaoDemanda)
	    query.select(cb.sum(root.<Double>get("receitaReal")));
	    
		return getEntityManager().createQuery(query).getSingleResult();
	}
	
	
	public Double obterSomaDespesasBruto(Boolean resultado) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Double> query = cb.createQuery(Double.class);
        final Root<FluxoDeCaixa> root = query.from(FluxoDeCaixa.class);

        Expression<Double> sumExpression = cb.sum(root.<Double>get("valor"));
	    Expression<Double> coalescedSum = cb.coalesce(sumExpression, 0.0);

	    query.select(coalescedSum)
	         .where(
	                cb.and(
	                        cb.equal(root.get("despRec"), DespesaReceita.DESPESA),
	                        cb.equal(root.get("lucro"), Lucro.BRUTO),
	                        cb.equal(root.get("resultado"), resultado)
	                )
	        );

        return getEntityManager().createQuery(query).getSingleResult();
    }
    public Double obterSomaReceitasOperacional(Boolean resultado) {
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<Double> query = cb.createQuery(Double.class);
    	Root<FluxoDeCaixa> root = query.from(FluxoDeCaixa.class);
    	
    	Expression<Double> sumExpression = cb.sum(root.<Double>get("valor"));
	    Expression<Double> coalescedSum = cb.coalesce(sumExpression, 0.0);

	    query.select(coalescedSum)
	         .where(
	                cb.and(
	                        cb.equal(root.get("despRec"), DespesaReceita.RECEITA),
	                        cb.equal(root.get("lucro"), Lucro.OPERACIONAL),
	                        cb.equal(root.get("resultado"), resultado)
	                )
	        );
    	
    	return getEntityManager().createQuery(query).getSingleResult();
    }
    
    public Double obterSomaDespesasOperacional(Boolean resultado) {
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<Double> query = cb.createQuery(Double.class);
    	Root<FluxoDeCaixa> root = query.from(FluxoDeCaixa.class);
    	
    	Expression<Double> sumExpression = cb.sum(root.<Double>get("valor"));
	    Expression<Double> coalescedSum = cb.coalesce(sumExpression, 0.0);

	    query.select(coalescedSum)
	         .where(
	                cb.and(
	                        cb.equal(root.get("despRec"), DespesaReceita.DESPESA),
	                        cb.equal(root.get("lucro"), Lucro.OPERACIONAL),
	                        cb.equal(root.get("resultado"), resultado)
	                )
	        );
    	
    	return getEntityManager().createQuery(query).getSingleResult();
    }
    public Double obterSomaReceitasLiquido(Boolean resultado) {
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<Double> query = cb.createQuery(Double.class);
    	Root<FluxoDeCaixa> root = query.from(FluxoDeCaixa.class);
    	
    	Expression<Double> sumExpression = cb.sum(root.<Double>get("valor"));
	    Expression<Double> coalescedSum = cb.coalesce(sumExpression, 0.0);

	    query.select(coalescedSum)
	         .where(
	                cb.and(
	                        cb.equal(root.get("despRec"), DespesaReceita.RECEITA),
	                        cb.equal(root.get("lucro"), Lucro.LIQUIDO),
	                        cb.equal(root.get("resultado"), resultado)
	                )
	        );
    	
    	return getEntityManager().createQuery(query).getSingleResult();
    }
    
    public Double obterSomaDespesasLiquido(Boolean resultado) {
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<Double> query = cb.createQuery(Double.class);
    	Root<FluxoDeCaixa> root = query.from(FluxoDeCaixa.class);
    	
    	Expression<Double> sumExpression = cb.sum(root.<Double>get("valor"));
	    Expression<Double> coalescedSum = cb.coalesce(sumExpression, 0.0);

	    query.select(coalescedSum)
	         .where(
	                cb.and(
	                        cb.equal(root.get("despRec"), DespesaReceita.DESPESA),
	                        cb.equal(root.get("lucro"), Lucro.LIQUIDO),
	                        cb.equal(root.get("resultado"), resultado)
	                )
	        );
    	
    	return getEntityManager().createQuery(query).getSingleResult();
    }
    
    public List<FluxoDeCaixa> obterRateiosBrutoOrdenados(Boolean resultado) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<FluxoDeCaixa> query = cb.createQuery(FluxoDeCaixa.class);
        Root<FluxoDeCaixa> root = query.from(FluxoDeCaixa.class);

        query.select(root)
             .where(
                cb.and(
                	cb.equal(root.get("lucro"), Lucro.BRUTO), cb.equal(root.get("resultado"), resultado)
                )
             )
             .orderBy(
                cb.desc(cb.selectCase()
                           .when(cb.equal(root.get("despRec"), DespesaReceita.RECEITA), 2)
                           .otherwise(1)),
                cb.asc(root.get("despRec"))
             );

        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<FluxoDeCaixa> obterRateiosOperacionalOrdenados(Boolean resultado) {
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<FluxoDeCaixa> query = cb.createQuery(FluxoDeCaixa.class);
    	Root<FluxoDeCaixa> root = query.from(FluxoDeCaixa.class);
    	
    	query.select(root)
    	.where(
                cb.and(
                	cb.equal(root.get("lucro"), Lucro.OPERACIONAL), cb.equal(root.get("resultado"), resultado)
                )
             )
    	.orderBy(
    			cb.desc(cb.selectCase()
    					.when(cb.equal(root.get("despRec"), DespesaReceita.RECEITA), 2)
    					.otherwise(1)),
    			cb.asc(root.get("despRec"))
    			);
    	
    	return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<FluxoDeCaixa> obterRateiosLiquidoOrdenados(Boolean resultado) {
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<FluxoDeCaixa> query = cb.createQuery(FluxoDeCaixa.class);
    	Root<FluxoDeCaixa> root = query.from(FluxoDeCaixa.class);
    	
    	query.select(root)
    	.where(
                cb.and(
                	cb.equal(root.get("lucro"), Lucro.LIQUIDO), cb.equal(root.get("resultado"), resultado)
                )
             )
    	.orderBy(
    			cb.desc(cb.selectCase()
    					.when(cb.equal(root.get("despRec"), DespesaReceita.RECEITA), 2)
    					.otherwise(1)),
    			cb.asc(root.get("despRec"))
    			);
    	
    	return getEntityManager().createQuery(query).getResultList();
    }
    
    public FluxoDeCaixa obtemFluxoPorId(Long id){
 		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<FluxoDeCaixa> cQuery = cBuilder.createQuery(FluxoDeCaixa.class);
		final Root<FluxoDeCaixa> rootAtendimento = cQuery.from(FluxoDeCaixa.class);

		cQuery.select(rootAtendimento)
		.where(cBuilder.equal(rootAtendimento.get("id"), id));
		

		FluxoDeCaixa resultado = getEntityManager().createQuery(cQuery).getSingleResult();

		return resultado;

	}
    
}
