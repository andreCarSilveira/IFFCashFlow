package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Caixa;

@Stateless
public class CaixaService extends GenericService<Caixa> {
	
	public CaixaService() {
		super(Caixa.class);
	}
	
 	@PersistenceContext
    private EntityManager entityManager;
 	
	public List<Caixa> listarCaixas(){
 		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Caixa> cQuery = cBuilder.createQuery(Caixa.class);
		final Root<Caixa> rootAtendimento = cQuery.from(Caixa.class);

		cQuery.select(rootAtendimento);
		cQuery.orderBy(cBuilder.asc(rootAtendimento.get("hora")));

		List<Caixa> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;

	}
}
