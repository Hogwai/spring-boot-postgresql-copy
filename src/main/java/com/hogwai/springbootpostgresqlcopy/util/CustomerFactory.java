package com.hogwai.springbootpostgresqlcopy.util;


import com.hogwai.springbootpostgresqlcopy.model.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hogwai.springbootmultitenancy.util.StringUtil.generateRandomString;

public class CustomerFactory {

    private CustomerFactory() {
    }

    public static List<Customer> generateCustomers(Integer number, Integer startId) {
        LocalDateTime creationDate = LocalDateTime.now();
        List<Customer> customers = new ArrayList<>(number);
        for (int i = startId; i < startId + number; i++) {
            Customer customer = Customer.builder()
                    .id((long) i)
                    .firstName(generateRandomString())
                    .lastName(generateRandomString())
                    .address(generateRandomString())
                    .city(generateRandomString())
                    .country(generateRandomString())
                    .creationDate(creationDate)
                    .build();
            customers.add(customer);
        }
        return customers;
    }
}
