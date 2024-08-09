package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.Socio;

@Stateless
public class SocioService extends GenericService<Socio> {

	public SocioService() {
		super(Socio.class);
	}
	
	public List<Socio> listarFuncionariosOrdenados() {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Socio> cQuery = cBuilder.createQuery(Socio.class);
		final Root<Socio> rootSocio = cQuery.from(Socio.class);

		final Expression<String> expNome = rootSocio.get("nome");

		cQuery.select(rootSocio);
		cQuery.orderBy(cBuilder.asc(expNome));

		List<Socio> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;
	}
	
	public Double somarCapitais() {
		if (getEntityManager() == null) {
            // Lida com o caso em que o EntityManager não foi injetado corretamente
            return 0.0;
        }
        final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
        final Root<Socio> root = criteriaQuery.from(Socio.class);

        // Certifique-se de que o tipo do campo "capital" seja Double
        criteriaQuery.select(criteriaBuilder.sum(root.<Double>get("capital")));

        List<Double> result = getEntityManager().createQuery(criteriaQuery).getResultList();

        // Retorna a soma dos capitais ou 0.0 se não houverem sócios
        return result.isEmpty() ? null : result.get(0);    
	}
	
	public Double somarPorcentagem() {
	    if (getEntityManager() == null) {
	        // Lida com o caso em que o EntityManager não foi injetado corretamente
	        return 0.0;
	    }

	    final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
	    final Root<Socio> root = criteriaQuery.from(Socio.class);

	    // Certifique-se de que o tipo do campo "porcentagem" seja Double
	    criteriaQuery.select(criteriaBuilder.sum(root.<Double>get("part")));

	    List<Double> result = getEntityManager().createQuery(criteriaQuery).getResultList();

	    // Retorna a soma das porcentagens ou 0.0 se não houverem sócios
	    return result.isEmpty() ? null : result.get(0);
	}
}
