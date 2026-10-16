package ru.kolosov.lab2.database;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class ItemTableInitializer {
    private static final String CREATE_SCHEMA_SQL = "CREATE SCHEMA IF NOT EXISTS %s";
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS %s.item (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL
    )
    """;

    public void initialize(DatabaseConnection databaseConnection) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute(CREATE_SCHEMA_SQL.formatted(databaseConnection.getSchema()));
            statement.execute(CREATE_TABLE_SQL.formatted(databaseConnection.getSchema()));
        }
    }
}
