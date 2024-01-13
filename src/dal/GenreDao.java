package dal;

import be.Genre;
import exceptions.MoviesException;
import javafx.beans.property.SimpleStringProperty;
import utility.ExceptionsMessages;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDao implements IGenreDao {

    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();


    @Override
    public List<Genre> retrieveGenres() throws MoviesException {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM GENRE ";
        try (Connection conn = CONNECTION_MANAGER.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                SimpleStringProperty genreName = getSimpleStringProperty(name);
                Genre genre = new Genre(id, genreName);
                genres.add(genre);
            }

        } catch (SQLException e) {
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED);
        }
        return genres;
    }

    private static SimpleStringProperty getSimpleStringProperty(String name) {
        SimpleStringProperty genreName = new SimpleStringProperty();
        genreName.setValue(name);
        return genreName;
    }
}
