package ru.kolosov.lab2.database;

import org.flywaydb.core.Flyway;

public class DatabaseMigrator {
    private final String url;
    private final String username;
    private final String password;
    private final String schema;

    public DatabaseMigrator(String url, String username, String password, String schema) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.schema = schema;
    }

    public void migrate() {
        Flyway.configure()
                .dataSource(url, username, password)
                .schemas(schema)
                .defaultSchema(schema)
                .createSchemas(true)
                .load()
                .migrate();
    }
}
