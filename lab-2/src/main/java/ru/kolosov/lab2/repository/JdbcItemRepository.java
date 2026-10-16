package ru.kolosov.lab2.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import ru.kolosov.lab2.database.DatabaseConnection;
import ru.kolosov.lab2.model.ItemEntity;

public class JdbcItemRepository implements ItemRepository{
    private final DatabaseConnection databaseConnection;

    public JdbcItemRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    
    private static final String INSERT_SQL = """
    INSERT INTO item (name, quantity)
    VALUES (?, ?)
    """;

    private static final String FIND_ID_SQL = """
    SELECT *
    FROM item
    WHERE id = (?);
    """;

    private static final String FIND_NAME_SQL = """
    SELECT *
    FROM item
    WHERE name = (?);
    """;

    private static final String FIND_ALL_SQL = """
    SELECT *
    FROM item
    """;

    private static final String UPDATE_SQL = """
    UPDATE item
    SET name = ?, quantity = ?
    WHERE id = ?
    """;

    private static final String DELETE_SQL = """
    DELETE FROM item
    WHERE id = ?
    """;

    public int save(ItemEntity item) {

        try (Connection connection = databaseConnection.getConnection();
        PreparedStatement statement =
        connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, item.getName());  // первый ? — name
            statement.setInt(2, item.getQuantity()); // второй ? — quantity

            statement.executeUpdate();

            try (var keys = statement.getGeneratedKeys()) { //
                if (keys.next()) {
                    return keys.getInt("id");
                }
            }
            throw new SQLException("База не вернула id новой записи");
            
            
        } catch (SQLException e) {
            throw new RuntimeException("Connection went wrong", e);
        }
       
    }

    public ItemEntity findById(int id){  
        try (Connection connection = databaseConnection.getConnection();
        PreparedStatement statement = 
        connection.prepareStatement(FIND_ID_SQL)) {

        statement.setInt(1, id);

        try (ResultSet result = statement.executeQuery()) {
            if (result.next()){
                ItemEntity item = new ItemEntity(id, result.getString("name"), result.getInt("quantity"));
                return item;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("База не вернула ничего");
        }
            
        } catch (SQLException e) {
            throw new RuntimeException(
                "Connection went wrong", e
            );
        }
    }

    public ItemEntity findByName(String name){

        try (Connection connection = databaseConnection.getConnection();
        PreparedStatement statement = 
        connection.prepareStatement(FIND_NAME_SQL)) {

        statement.setString(1, name);

        try (ResultSet result = statement.executeQuery()) {
            if (result.next()){
                ItemEntity item = new ItemEntity(result.getInt("id"), result.getString("name"), result.getInt("quantity"));
                return item;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("База не вернула ничего");
        }
            
        } catch (SQLException e) {
            throw new RuntimeException(
                "Connection went wrong", e
            );
        }
    }

    public List<ItemEntity> findAll(){
        
        List<ItemEntity> items = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection();
        PreparedStatement statement = 
        connection.prepareStatement(FIND_ALL_SQL)) {

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()){
                while(resultSet.next()){

                    ItemEntity item = new ItemEntity(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("quantity"));
                    items.add(item);

                }
                return items;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("База не вернула ничего");
        }
            
        } catch (SQLException e) {
            throw new RuntimeException(
                "Connection went wrong", e
            );
        }
    }

    public boolean update(ItemEntity item){

        try (Connection connection = databaseConnection.getConnection();
        PreparedStatement statement = 
        connection.prepareStatement(UPDATE_SQL)) {

        statement.setString(1, item.getName());
        statement.setInt(2, item.getQuantity());
        statement.setInt(3, item.getId());


        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
       
        } catch (SQLException e) {
            throw new RuntimeException(
                "Connection went wrong", e
            );
        }
    }
    
    public void deleteById(int id){
        
        try (Connection connection = databaseConnection.getConnection();
        PreparedStatement statement = 
        connection.prepareStatement(DELETE_SQL)) {

        statement.setInt(1, id);

        statement.executeUpdate();
       
        } catch (SQLException e) {
            throw new RuntimeException(
                "Connection went wrong", e
            );
        }
    }

}
