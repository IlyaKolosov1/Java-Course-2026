package ru.kolosov.lab2;

import ru.kolosov.lab2.database.DatabaseConnection;
import ru.kolosov.lab2.database.ItemTableInitializer;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException{
        final String DATABASE_USERNAME = System.getenv("DATABASE_USERNAME");
        final String DATABASE_URL = System.getenv("DATABASE_URL");
        final String DATABASE_PASSWORD = System.getenv("DATABASE_PASSWORD");
        DatabaseConnection db = new DatabaseConnection(
            DATABASE_URL,
            DATABASE_USERNAME,
            DATABASE_PASSWORD
        );

        ItemTableInitializer initializer = new ItemTableInitializer();

        initializer.initialize(db);
        System.out.println("Таблица item создана или уже существовала");

    }


}

