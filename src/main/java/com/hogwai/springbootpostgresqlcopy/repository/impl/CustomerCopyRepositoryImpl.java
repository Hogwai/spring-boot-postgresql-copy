package com.hogwai.springbootpostgresqlcopy.repository.impl;

import com.hogwai.springbootpostgresqlcopy.model.Customer;
import com.hogwai.springbootpostgresqlcopy.repository.CustomerCopyRepository;
import com.hogwai.springbootpostgresqlcopy.repository.util.CopyMapper;
import com.hogwai.springbootpostgresqlcopy.repository.util.CopyUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CustomerCopyRepositoryImpl implements CustomerCopyRepository {

    public static final List<String> CUSTOMER_COLUMNS = List.of(
            "id",
            "first_name",
            "last_name",
            "address",
            "city",
            "country",
            "creation_date"
    );
    public static final String CUSTOMER = "customer";
    private final CopyUtils copyUtils;
    private final DataSource dataSource;

    public CustomerCopyRepositoryImpl(DataSource dataSource,
                                      CopyUtils copyUtils) {
        this.dataSource = dataSource;
        this.copyUtils = copyUtils;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertWithCopy(List<Customer> customers) {
        CopyMapper<Customer> customerMapper = customer -> new String[]{
                String.valueOf(customer.getId()),
                CopyUtils.escapeCopyValue(customer.getFirstName()),
                CopyUtils.escapeCopyValue(customer.getLastName()),
                CopyUtils.escapeCopyValue(customer.getAddress()),
                CopyUtils.escapeCopyValue(customer.getCity()),
                CopyUtils.escapeCopyValue(customer.getCountry()),
                customer.getCreationDate().toString()
        };

        copyUtils.insertWithCopy(CUSTOMER, CUSTOMER_COLUMNS, customers, customerMapper, dataSource);
    }
}
