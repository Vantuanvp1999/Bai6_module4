package com.example.thuchanh1.repository;

import com.example.thuchanh1.model.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
@Repository
@Transactional
public class CustomerRepository implements ICustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(long id) {
       TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.id = :id", Customer.class);
       query.setParameter("id", id);
       try{
       return query.getSingleResult();
       }catch (Exception e){
           return null;
       }

    }

    @Override
    public void save(Customer customer) {
    if (customer != null) {
        entityManager.merge(customer);
    }else {
        entityManager.persist(customer);
    }
    }

    @Override
    public void delete(long id) {
        Customer customer = findById(id);
        if (customer != null) {
            entityManager.remove(customer);
        }
    }
}
