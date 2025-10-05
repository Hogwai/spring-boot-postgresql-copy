package com.hogwai.springbootpostgresqlcopy.repository;

import com.hogwai.springbootpostgresqlcopy.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCopyRepository {
    void insertWithCopy(List<Customer> customers);
}
