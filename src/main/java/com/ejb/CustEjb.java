package com.ejb;

import com.entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
@Stateless
public class CustEjb {

    @PersistenceContext(unitName = "sal")
    private EntityManager em;

    public void save(Customer customer) {
        em.persist(customer);
    }

    public void update(Customer customer) {
        em.merge(customer);
    }

    public void delete(Customer customer) {
           customer= em.find(Customer.class,customer.getId());
            em.remove(customer);

        }

    public List<Customer> getAllCutomers() {
        Query query = em.createQuery("select a from Customer a ");
        return query.getResultList();
    }

    public boolean customerCheckCode(Customer customer){
        Query query = em.createQuery("select c from Customer as c where c.code = :code").setParameter("code",customer.getCode());
        if(query.getResultList().isEmpty()){
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean customerCheckName(Customer customer){
        Query query = em.createQuery("select c from Customer as c where c.name = :name").setParameter("name",customer.getName());
        if(query.getResultList().isEmpty()){
            return false;
        }
        else
        {
            return true;
        }
    }

    public EntityManager getEm() {
        return em;
    }
}
