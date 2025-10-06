package com.hogwai.springbootpostgresqlcopy.service;


import com.hogwai.springbootpostgresqlcopy.model.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    void insertCustomers(Integer customerNumber);

    @Transactional
    void bulkInsertCustomers(Integer customerNumber);
}
