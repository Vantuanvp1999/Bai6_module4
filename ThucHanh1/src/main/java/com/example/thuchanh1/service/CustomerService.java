package com.example.thuchanh1.service;

import com.example.thuchanh1.model.Customer;
import com.example.thuchanh1.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;


    @Override
    public List<Customer> findAll() {
       return iCustomerRepository.findAll();
    }

    @Override
    public void save(Customer customer) {
        iCustomerRepository.save(customer);
    }

    @Override
    public void delete(long id) {
        iCustomerRepository.delete(id);
    }

    @Override
    public Customer findById(long id) {
        return iCustomerRepository.findById(id);
    }
}
