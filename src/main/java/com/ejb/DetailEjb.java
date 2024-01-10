package com.ejb;
import com.entity.FactDetail;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless

public class DetailEjb {
@PersistenceContext(unitName = "sal")
    private EntityManager em;

    public void save(FactDetail detail) {
        em.persist(detail);

    }
    public void update(FactDetail detail) {
        em.merge(detail);
    }

    public void delete(FactDetail detail) {
        detail= em.find(FactDetail.class,detail.getId());
        em.remove(detail);

    }

    public List<FactDetail> getAllDetails() {
        Query query = em.createQuery("select a from FactDetail a ");
        return query.getResultList();
    }

}
