package com.hogwai.springbootpostgresqlcopy.service.impl;


import com.hogwai.springbootpostgresqlcopy.model.Customer;
import com.hogwai.springbootpostgresqlcopy.repository.CustomerRepository;
import com.hogwai.springbootpostgresqlcopy.service.CustomerService;
import com.hogwai.springbootpostgresqlcopy.util.CustomerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    @Override
    public void insertCustomers(Integer customerNumber) {
        List<Customer> customers = CustomerFactory.generateCustomers(customerNumber, getMaxCustomerId() + 1);
        customerRepository.insertWithCopy(customers);
        customerRepository.incrementSequence();
    }

    @Transactional
    @Override
    public void bulkInsertCustomers(Integer customerNumber) {
        List<Customer> customers = CustomerFactory.generateCustomers(customerNumber, getMaxCustomerId() + 1);
        customerRepository.bulkInsert(customers);
        customerRepository.incrementSequence();
    }

    private int getMaxCustomerId() {
        Integer lastCustomerId = customerRepository.findMaxCustomerId();
        return lastCustomerId == null ? 0 : lastCustomerId;
    }
}
