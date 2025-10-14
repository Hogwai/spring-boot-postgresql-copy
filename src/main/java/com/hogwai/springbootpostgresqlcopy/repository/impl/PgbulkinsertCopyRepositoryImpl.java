package com.hogwai.springbootpostgresqlcopy.repository.impl;

import com.hogwai.springbootpostgresqlcopy.model.Customer;
import com.hogwai.springbootpostgresqlcopy.model.CustomerMapping;
import com.hogwai.springbootpostgresqlcopy.repository.PgbulkinsertCopyRepository;
import de.bytefish.pgbulkinsert.PgBulkInsert;
import de.bytefish.pgbulkinsert.util.PostgreSqlUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@Repository
public class PgbulkinsertCopyRepositoryImpl implements PgbulkinsertCopyRepository {

    private final DataSource dataSource;

    public PgbulkinsertCopyRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bulkInsert(List<Customer> customers) {
        PgBulkInsert<Customer> bulkInsert = new PgBulkInsert<>(new CustomerMapping());

        try (Connection conn = dataSource.getConnection()) {
            bulkInsert.saveAll(PostgreSqlUtils.getPGConnection(conn), customers);
        } catch (Exception e) {
            throw new RuntimeException("Error while bulk inserting", e);
        }
    }
}
