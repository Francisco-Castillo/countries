/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcastillo.countries.ejb;

import com.fcastillo.countries.entidades.Pais;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author fcastillo
 */
@Stateless
public class PaisFacade extends AbstractFacade<Pais> implements PaisFacadeLocal {

  @PersistenceContext(unitName = "com.fcastillo_countries_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public PaisFacade() {
    super(Pais.class);
  }

  @Override
  public List<Pais> findByParams(int start, int size, String sortField, String sortOrder, Map<String, Object> filters) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Pais> criteriaQuery = cb.createQuery(Pais.class);
    Root<Pais> root = criteriaQuery.from(Pais.class);
    List<Predicate> listaPredicados = new ArrayList<>();
    List<Pais> listaPaises = new ArrayList<>();
    criteriaQuery.select(root);

    if (sortField != null) {
      criteriaQuery.orderBy(sortOrder.equals("asc") ? cb.asc(root.get(sortField)) : cb.desc(root.get(sortField)));
    }

    try {

      if (filters != null && filters.size() > 0) {

        if (filters.get("id") != null) {
          Predicate predicadoSearch = cb.equal(root.get("id"), filters.get("id"));
          // Predicate predicadoSearch = cb.like(cb.lower(root.get("id")), "%" + filters.get("id").toString().toLowerCase() + "%");
          listaPredicados.add(predicadoSearch);
        }

        if (filters.get("nombreLargo") != null) {
          Predicate predicadoSearch = cb.like(cb.lower(root.get("nombrelargo")), "%" + filters.get("nombreLargo").toString().toLowerCase() + "%");
          listaPredicados.add(predicadoSearch);
        }

        if (filters.get("nombreCorto") != null) {
          Predicate predicadoSearch = cb.like(cb.lower(root.get("nombrecorto")), "%" + filters.get("nombreCorto").toString().toLowerCase() + "%");
          listaPredicados.add(predicadoSearch);
        }
        
        if (filters.get("capital") != null) {
          Predicate predicadoSearch = cb.like(cb.lower(root.get("capital")), "%" + filters.get("capital").toString().toLowerCase() + "%");
          listaPredicados.add(predicadoSearch);
        }

        if (listaPredicados.size() > 0) {
          listaPredicados.forEach((p) -> {
            criteriaQuery.where(listaPredicados.toArray(new Predicate[listaPredicados.size()]));
          });
        }
      }
      TypedQuery<Pais> query = em.createQuery(criteriaQuery);
      query.setFirstResult(start);
      query.setMaxResults(size);
      listaPaises = query.getResultList();
    } catch (Exception e) {
      System.out.println("findByParams() " + e.getLocalizedMessage());
    }
    return listaPaises;
  }
}
