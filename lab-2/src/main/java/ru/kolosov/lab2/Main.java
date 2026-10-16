package ru.kolosov.lab2;

import ru.kolosov.lab2.database.DatabaseConnection;
import ru.kolosov.lab2.database.DatabaseMigrator;
import ru.kolosov.lab2.model.ItemEntity;
import ru.kolosov.lab2.repository.ItemRepository;
import ru.kolosov.lab2.repository.JdbcItemRepository;

public class Main {
    public static void main(String[] args) {
        String url = requireEnvironmentVariable("DATABASE_URL");
        String username = requireEnvironmentVariable("DATABASE_USERNAME");
        String password = requireEnvironmentVariable("DATABASE_PASSWORD");
        String schema = requireEnvironmentVariable("DATABASE_SCHEMA");

        DatabaseConnection databaseConnection = new DatabaseConnection(
                url, username, password, schema);

        new DatabaseMigrator(url, username, password, schema).migrate();
        ItemRepository repository = new JdbcItemRepository(databaseConnection);

        ItemEntity newItem = new ItemEntity("Lab 2 demo item", 10);
        int id = repository.save(newItem);
        System.out.println("save returned id: " + id);

        System.out.println("findById: " + repository.findById(id));
        System.out.println("findByName: " + repository.findByName(newItem.getName()));
        System.out.println("findAll count: " + repository.findAll().size());

        ItemEntity updatedItem = new ItemEntity(id, "Lab 2 demo item updated", 12);
        System.out.println("update returned: " + repository.update(updatedItem));
        System.out.println("updated item: " + repository.findById(id));

        repository.deleteById(id);
        System.out.println("findById after delete: " + repository.findById(id));
    }

    private static String requireEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing environment variable: " + name);
        }
        return value;
    }
}

