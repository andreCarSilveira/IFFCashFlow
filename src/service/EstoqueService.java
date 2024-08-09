package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Caixa;
import modelo.Estoque;

@Stateless
public class EstoqueService extends GenericService<Estoque>{

	public EstoqueService() {
		super(Estoque.class);
	}

	 	@PersistenceContext
	    private EntityManager entityManager;
	 	
	 	
		
		public List<Estoque> listarEstoques(){
	 		
			final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<Estoque> cQuery = cBuilder.createQuery(Estoque.class);
			final Root<Estoque> rootAtendimento = cQuery.from(Estoque.class);

			cQuery.select(rootAtendimento);
			cQuery.orderBy(cBuilder.asc(rootAtendimento.get("hora")));

			List<Estoque> resultado = getEntityManager().createQuery(cQuery).getResultList();

			return resultado;

		}
		
		
	 	
	 	
}