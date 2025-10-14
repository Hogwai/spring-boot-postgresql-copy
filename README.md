# Using the COPY command with Spring Boot
This repository shows a few ways to leverage the COPY command with Spring Boot

## Using the CopyManager
```java
public <T> void insertWithCopy(String tableName,
                               List<String> columns,
                               List<T> entities,
                               CopyMapper<T> mapper,
                               DataSource dataSource) {
    if (CollectionUtils.isEmpty(entities)) {
        throw new IllegalArgumentException("No entities provided");
    }
    if (CollectionUtils.isEmpty(columns)) {
        throw new IllegalArgumentException("No columns provided");
    }

    String sql = COPY_STMT.formatted(tableName, String.join(", ", columns));

    try (Connection connection = dataSource.getConnection();
         StringWriter writer = new StringWriter()) {

        PGConnection pgConnection = connection.unwrap(PGConnection.class);
        CopyManager copyManager = new CopyManager((BaseConnection) pgConnection);

        for (T entity : entities) {
            String[] row = mapper.toCopyRow(entity);
            if (row.length != columns.size()) {
                throw new IllegalStateException("Mismatch between rows (%d) and columns (%d)"
                        .formatted(row.length, columns.size()));
            }
            writer.write(String.join("\t", row));
            writer.write("\n");
        }

        copyManager.copyIn(sql, new StringReader(writer.toString()));

    } catch (Exception e) {
        throw new RuntimeException("Error while inserting", e);
    }
}
```

## Using the library [PgBulkInsert](https://github.com/PgBulkInsert/PgBulkInsert)
### Using the class PgBulkInsert
```java
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
```
```java
public void bulkInsert(List<Customer> customers) {
    PgBulkInsert<Customer> bulkInsert = new PgBulkInsert<>(new CustomerMapping());

    try (Connection conn = dataSource.getConnection()) {
        bulkInsert.saveAll(PostgreSqlUtils.getPGConnection(conn), customers);
    } catch (Exception e) {
        throw new RuntimeException("Error while bulk inserting", e);
    }
}
```