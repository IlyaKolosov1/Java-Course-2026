package ru.kolosov.lab2.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class DatabaseConnection {
    private final String url;
    private final String username;
    private final String password;
    private final String schema;

    public DatabaseConnection(String url, String username, String password, String schema) {
        this.url = url;
        this.username = username;
        this.password = password;
        if (schema == null || !schema.matches("[a-z][a-z0-9_]*")) {
            throw new IllegalArgumentException("DATABASE_SCHEMA must be a lowercase SQL identifier");
        }
        this.schema = schema;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public String getSchema() {
        return schema;
    }
}
