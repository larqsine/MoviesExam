package dal;

import be.Category;
import exceptions.MoviesException;
import utility.ExceptionsMessages;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao implements ICategoryDao {

    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();


    private List<Category> retrieveCategories() throws MoviesException {
        String sql = "SELECT * FROM Category";
        List<Category> categories = new ArrayList<>();

        try (Connection connection = CONNECTION_MANAGER.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Category category = new Category(id, name);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED, e.getCause());
        }
    return categories;
    }

    @Override
    public List<Category> getCategories() throws MoviesException {
        return retrieveCategories();
    }
}
