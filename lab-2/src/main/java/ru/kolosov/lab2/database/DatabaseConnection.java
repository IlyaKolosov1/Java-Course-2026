package ru.kolosov.lab2.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable {
    // Пул хранит и повторно выдаёт JDBC-подключения вместо создания нового каждый раз.
    private final HikariDataSource dataSource;
    private final String schema;

    public DatabaseConnection(String url, String username, String password, String schema) {
        if (schema == null || !schema.matches("[a-z][a-z0-9_]*")) {
            throw new IllegalArgumentException("DATABASE_SCHEMA must be a lowercase SQL identifier");
        }
        this.schema = schema;

        // Настройки соединений, которые HikariCP будет открывать и держать в пуле.
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setSchema(schema);

        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        // close() у такого подключения возвращает его в пул для повторного использования.
        return dataSource.getConnection();
    }

    public String getSchema() {
        return schema;
    }

    @Override
    public void close() {
        // Закрываем весь пул один раз, когда приложение завершает работу.
        dataSource.close();
    }
}
