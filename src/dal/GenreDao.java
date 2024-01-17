package dal;

import be.Category;
import be.Genre;
import exceptions.MoviesException;
import javafx.beans.property.SimpleStringProperty;
import utility.ExceptionsMessages;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreDao implements IGenreDao {

    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();


    @Override
    public Map<String,Genre> retrieveGenres() throws MoviesException {
        Map<String,Genre> genres = new HashMap<>();
        String sql = "SELECT * FROM GENRE ";
        try (Connection conn = CONNECTION_MANAGER.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                SimpleStringProperty genreName = getSimpleStringProperty(name);
                Genre genre = new Genre(id, genreName);
                genres.put(genre.getName(),genre);
            }

        } catch (SQLException e) {
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED);
        }
        return genres;
    }

    @Override
    public Map<String, Category> retrieveCategories() throws MoviesException {
        Map<String,Category> categories = new HashMap<>();
        String sql = "SELECT * FROM Category ";
        try (Connection conn = CONNECTION_MANAGER.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Category cat = new Category(id, name);
                categories.put(cat.getName(),cat);
            }
        } catch (SQLException e) {
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED);
        }
        return categories;
    }

    private static SimpleStringProperty getSimpleStringProperty(String name) {
        SimpleStringProperty genreName = new SimpleStringProperty();
        genreName.setValue(name);
        return genreName;
    }
}
