package com.hogwai.springbootpostgresqlcopy.repository;

import com.hogwai.springbootpostgresqlcopy.model.Customer;
import de.bytefish.pgbulkinsert.bulkprocessor.BulkProcessor;
import org.postgresql.copy.CopyManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCopyRepository {
    /**
     * Insert with custom implementation around the {@link CopyManager}
     *
     * @param customers customers
     */
    void insertWithCopy(List<Customer> customers);

    /**
     * Insert with the {@link BulkProcessor}
     *
     * @param customers customers
     */
    void bulkInsert(List<Customer> customers);
}
