package ru.kolosov.lab2.repository;

import ru.kolosov.lab2.database.DatabaseConnection;
import ru.kolosov.lab2.model.ItemEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcItemRepository implements ItemRepository {
    private static final String INSERT_SQL = """
            INSERT INTO %s.item (name, quantity)
            VALUES (?, ?)
            """;
    private static final String FIND_ID_SQL = """
            SELECT id, name, quantity
            FROM %s.item
            WHERE id = ?
            """;
    private static final String FIND_NAME_SQL = """
            SELECT id, name, quantity
            FROM %s.item
            WHERE name = ?
            ORDER BY id
            LIMIT 1
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, name, quantity
            FROM %s.item
            ORDER BY id
            """;
    private static final String UPDATE_SQL = """
            UPDATE %s.item
            SET name = ?, quantity = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM %s.item
            WHERE id = ?
            """;

    private final DatabaseConnection databaseConnection;

    public JdbcItemRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public int save(ItemEntity item) {
        if (item == null) {
            throw new IllegalArgumentException("Товар не должен быть null");
        }

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_SQL.formatted(databaseConnection.getSchema()),
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getQuantity());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt("id");
                }
            }
            throw new SQLException("PostgreSQL не вернул id созданного товара");
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить товар", e);
        }
    }

    @Override
    public ItemEntity findById(int id) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     FIND_ID_SQL.formatted(databaseConnection.getSchema()))) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapItem(result);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти товар по id", e);
        }
    }

    @Override
    public ItemEntity findByName(String name) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     FIND_NAME_SQL.formatted(databaseConnection.getSchema()))) {
            statement.setString(1, name);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapItem(result);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти товар по имени", e);
        }
    }

    @Override
    public List<ItemEntity> findAll() {
        List<ItemEntity> items = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     FIND_ALL_SQL.formatted(databaseConnection.getSchema()));
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                items.add(mapItem(resultSet));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить список товаров", e);
        }
    }

    @Override
    public boolean update(ItemEntity item) {
        if (item == null || item.getId() == null) {
            throw new IllegalArgumentException("Для обновления нужны товар и его id");
        }

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     UPDATE_SQL.formatted(databaseConnection.getSchema()))) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getQuantity());
            statement.setInt(3, item.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось обновить товар", e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     DELETE_SQL.formatted(databaseConnection.getSchema()))) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить товар по id", e);
        }
    }

    private ItemEntity mapItem(ResultSet result) throws SQLException {
        return new ItemEntity(
                result.getInt("id"),
                result.getString("name"),
                result.getInt("quantity"));
    }
}
