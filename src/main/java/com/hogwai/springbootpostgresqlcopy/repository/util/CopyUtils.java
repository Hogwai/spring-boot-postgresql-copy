package com.hogwai.springbootpostgresqlcopy.repository.util;

import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.List;

@Component
public class CopyUtils {

    private static final String COPY_STMT = "COPY %s (%s) FROM STDIN";

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

    public static String escapeCopyValue(String value) {
        if (value == null) {
            // Null symbol for the copy command
            return "\\N";
        }
        return value.replace("\\", "\\\\")
                    .replace("\t", "\\t")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }
}
