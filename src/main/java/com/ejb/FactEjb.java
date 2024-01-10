package com.ejb;


import com.entity.FactHeader;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class FactEjb {
    @PersistenceContext(unitName = "sal")
    private EntityManager em;


    public void save(FactHeader factHeader) {
        em.persist(factHeader);

    }
    public void update(FactHeader header) {
        em.merge(header);
    }

    public void delete(FactHeader header) {
        // جهت کار با انتیتی منیجر باید ابتدا آن را پیدا کرد چون امکان دارد مربوط به سشن دیگری باشد
        header= em.find(FactHeader.class,header.getId());
        em.remove(header);

    }

    public List<FactHeader> getAllHeaders() {
        Query query = em.createQuery("select a from FactHeader a ");
        return query.getResultList();
    }

    public FactHeader finedFetch(FactHeader header) {
       return em.createQuery("select h from FactHeader h " +
                "left join fetch h.customers c " +
                "left join fetch h.details d " +
                "left join fetch d.good " +
                "where h=:factheader",FactHeader.class).setParameter("factheader",header).getSingleResult();
    }
}
