package com.hogwai.springbootpostgresqlcopy.repository;


import com.hogwai.springbootpostgresqlcopy.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>, CustomerCopyRepository, PgbulkinsertCopyRepository {

    @Query("select max(c.id) from Customer c")
    Integer findMaxCustomerId();

    @Query(value = "select setval('cust_seq', (select max(id) from customer) + 1)", nativeQuery = true)
    void incrementSequence();
}
