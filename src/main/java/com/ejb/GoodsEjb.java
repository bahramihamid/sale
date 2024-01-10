package com.ejb;

import com.entity.Goods;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class GoodsEjb {
    @PersistenceContext(unitName = "sal")
    private EntityManager em;

    public void save(Goods good) {
        em.persist(good);
    }

    public void delete(Goods good){
        good = em.find(Goods.class,good.getId() );
        em.remove(good);
    }

    public void update(Goods good){
        em.merge(good);
    }

    public List<Goods> getGoodsSet(){
        Query query = em.createQuery("select g from Goods as g");
        return query.getResultList();
    }

    public boolean checkGoodName (Goods good){
        Query query = em.createQuery("select g from  Goods as g where g.name =:name").setParameter("name",good.getName());
        if (query.getResultList().isEmpty()) {
            return false;
        }
        else {
            return true;
        }

    }

    public boolean checkGoodCode (Goods good){
        Query query = em.createQuery("select g from  Goods as g where g.code =:code").setParameter("code",good.getCode());
        if (query.getResultList().isEmpty()) {
            return false;
        }
        else {
            return true;
        }

    }

    public EntityManager getEm() {
        return em;
    }
}
