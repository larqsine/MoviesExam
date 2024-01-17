package dal;

import be.Category;
import be.Movie;
import exceptions.MoviesException;
import utility.ExceptionsMessages;

import java.sql.*;
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

    @Override
    public boolean createCategory(String  categoryTitle) throws MoviesException {
        String sql = "INSERT INTO Category values (?)";
        try(Connection conn = CONNECTION_MANAGER.getConnection()){
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,categoryTitle);
             return psmt.execute();
        } catch (SQLException e) {
            throw new MoviesException(e.getMessage());
        }
    }

    @Override
    public boolean updateCategory (int categoryId, String newTitle) throws MoviesException {
        String sql = "UPDATE Category SET name=? WHERE Id=?";
        try (Connection connection = CONNECTION_MANAGER.getConnection()){
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, newTitle);
            psmt.setInt(2, categoryId);
            int rowsAffected = psmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new MoviesException(ExceptionsMessages.DB_UNSUCCESFULL, e.getCause());
        }
    }

    @Override
    public boolean deleteCategory(int id) throws MoviesException {
        String sql = "DELETE FROM Category WHERE Id=?";
        try (Connection connection = CONNECTION_MANAGER.getConnection()){
            PreparedStatement psmt = null;
            int rowsAffected = 0;
            boolean deleteFromTable = deleteCategoryFromTable(id, connection, psmt);
            if (deleteFromTable) {
                psmt = connection.prepareStatement(sql);
                psmt.setInt(1, id);
                rowsAffected = psmt.executeUpdate();
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new MoviesException(ExceptionsMessages.DB_UNSUCCESFULL, e.getCause());
        }
    }

    private boolean deleteCategoryFromTable(int categoryId, Connection connection, PreparedStatement psmt) throws MoviesException {
        String sql = "DELETE FROM Category WHERE Id=?";
        try{
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1, categoryId);
            int rows = psmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e){
            throw new MoviesException(ExceptionsMessages.DB_UNSUCCESFULL, e.getCause());
        }
    }


    /**@Override
    public boolean de(Category category) throws MoviesException {
        System.out.println(category.getName());
        System.out.println(category.getId());
        String sqlDeleteCategory = "DELETE FROM Category WHERE id=?";
        try (Connection connection = CONNECTION_MANAGER.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement psmtDelete = connection.prepareStatement(sqlDeleteCategory)) {
                psmtDelete.setInt(1, category.getId());
                int rows = psmtDelete.executeUpdate();
                FileHandler fileHandler = FileHandler.getInstance();
//                if (!fileHandler.deleteSongLocal(movie.getFileLink())) {
//                    connection.rollback();
//                    return false;
//                }
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                throw new MoviesException(ExceptionsMessages.TRANSACTION_FAILED, e);
            }
        } catch (SQLException e) {
            throw new MoviesException(ExceptionsMessages.NO_DATABASE_CONNECTION, e);
        }
    }*/
}
