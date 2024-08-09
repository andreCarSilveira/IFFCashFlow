package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Item;

@Stateless
public class ItemService extends GenericService<Item> {

	public ItemService() {
		super(Item.class);
	}
	
 	@PersistenceContext
    private EntityManager entityManager;
 	
 	public List<Item> listarItens(){
 		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Item> cQuery = cBuilder.createQuery(Item.class);
		final Root<Item> rootAtendimento = cQuery.from(Item.class);

		cQuery.select(rootAtendimento);
		cQuery.orderBy(cBuilder.asc(rootAtendimento.get("nome")));

		List<Item> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;

	}

}
