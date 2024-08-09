package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.Produto;

@Stateless
public class ProdutoService extends GenericService<Produto>{

	public ProdutoService() {
		super(Produto.class);
	}

	 	@PersistenceContext
	    private EntityManager entityManager;
	 	
	 	public List<Produto> listarProdutos(){
			final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<Produto> cQuery = cBuilder.createQuery(Produto.class);
			final Root<Produto> rootAtendimento = cQuery.from(Produto.class);

			cQuery.select(rootAtendimento);
			cQuery.orderBy(cBuilder.asc(rootAtendimento.get("nome")));

			List<Produto> resultado = getEntityManager().createQuery(cQuery).getResultList();

			return resultado;

		}

	 	public List<Produto> listarProdutosPorNome(String nome){
			final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<Produto> cQuery = cBuilder.createQuery(Produto.class);
			final Root<Produto> rootAtendimento = cQuery.from(Produto.class);

			final Expression<String> expdescricao = rootAtendimento.get("nome");

			cQuery.select(rootAtendimento);
			cQuery.where(cBuilder.like(expdescricao, "%"+ nome +"%"));
			cQuery.orderBy(cBuilder.asc(rootAtendimento.get("nome")));

			List<Produto> resultado = getEntityManager().createQuery(cQuery).getResultList();

			return resultado;

		}
}