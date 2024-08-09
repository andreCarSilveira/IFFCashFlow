package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Empresa;

@Stateless
public class EmpresaService extends GenericService<Empresa> {

	public EmpresaService() {
		super(Empresa.class);
	}
	
	public Empresa buscarEmpresa() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Empresa> criteriaQuery = criteriaBuilder.createQuery(Empresa.class);
        Root<Empresa> root = criteriaQuery.from(Empresa.class);

        criteriaQuery.select(root);

        TypedQuery<Empresa> typedQuery = getEntityManager().createQuery(criteriaQuery);
        typedQuery.setMaxResults(1); // Limita o resultado a apenas uma empresa

        List<Empresa> empresas = typedQuery.getResultList();
        if (!empresas.isEmpty()) {
            return empresas.get(0); 
        } else {
            return null; 
        }

    }

}
