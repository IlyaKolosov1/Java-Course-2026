package ru.kolosov.lab2.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class DatabaseConnection {
    private final String url;
    private final String username;
    private final String password;

    public DatabaseConnection(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
}
