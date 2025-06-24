package com.example.thuchanh2.repository;

import com.example.thuchanh2.model.Customer;

public interface ICustomerRepository {
    boolean saveWithStoredProcedure(Customer customer);
}
