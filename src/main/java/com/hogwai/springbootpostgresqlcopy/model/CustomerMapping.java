package com.hogwai.springbootpostgresqlcopy.model;

import de.bytefish.pgbulkinsert.mapping.AbstractMapping;

public class CustomerMapping extends AbstractMapping<Customer> {
    public CustomerMapping() {
        super("public", "customer");
        mapLong("id", Customer::getId);
        mapText("first_name", Customer::getFirstName);
        mapText("last_name", Customer::getLastName);
        mapText("country", Customer::getCountry);
        mapText("address", Customer::getAddress);
        mapText("city", Customer::getCity);
        mapTimeStamp("creation_date", Customer::getCreationDate);
        mapTimeStamp("update_date", Customer::getUpdateDate);
    }
}