package com.hogwai.springbootpostgresqlcopy.repository;

import com.hogwai.springbootpostgresqlcopy.model.Customer;
import de.bytefish.pgbulkinsert.bulkprocessor.BulkProcessor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PgbulkinsertCopyRepository {
    /**
     * Insert with the {@link BulkProcessor}
     *
     * @param customers customers
     */
    void bulkInsert(List<Customer> customers);
}
