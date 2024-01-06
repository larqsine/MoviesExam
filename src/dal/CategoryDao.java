package dal;

import be.Category;
import exceptions.MoviesException;
import utility.ExceptionsMessages;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDao implements ICategoryDao {

    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();


    private Map<Integer,Category> retrieveCategories() throws MoviesException {
        String sql = "SELECT * FROM Category";
         Map<Integer,Category> categoriesMap=  new HashMap<>();

        try (Connection connection = CONNECTION_MANAGER.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Category category = new Category(id, name);
                categoriesMap.put(category.getId(),category);
            }
        } catch (SQLException e) {
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED, e.getCause());
        }
    return categoriesMap;
    }

    @Override
    public Map<Integer,Category> getCategories() throws MoviesException {
        return retrieveCategories();
    }
}
