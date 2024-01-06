package dal;

import be.Category;
import be.Movie;
import exceptions.MoviesException;
import utility.ExceptionsMessages;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class MovieDao implements IMovieDao {
    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();

    /**
     * retrieves the movie data from the database
     */
    private Map<Integer, Movie> retrieveMovies(int categoryId) throws MoviesException {
        System.out.println(categoryId);
        Map<Integer, Movie> moviesMap = new HashMap<>();
        Movie movie;
        Category category;
        try (Connection conn = CONNECTION_MANAGER.getConnection()) {
            String sql = "SELECT m.id,m.name, m.rating, m.filelink, m.lastview, m.personalRating,c.id,c.name FROM CatMovie cm " +
                    "JOIN Movie m  on m.id = cm.MovieId " +
                    "JOIN Category c on c.id = cm.CategoryId " +
                    "WHERE c.id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getDate(5));
                    int movieId = rs.getInt(1);
                    String movieName = rs.getString(2);
                    double rating = rs.getDouble(3);
                    String fileLink = rs.getString(4);
                    Date lastView = rs.getDate(5);
                    double personalRating = rs.getDouble(6);
                    int catId = rs.getInt(7);
                    String categoryName = rs.getString(8);

                    movie = new Movie(movieId, movieName, rating, fileLink, lastView, personalRating, null);
                    System.out.println(movie);
                    category = new Category(catId, categoryName);
                    System.out.println(category);

                    if (moviesMap.containsKey(movie.getId())) {
                        moviesMap.get(movie.getId()).getCategories().add(category);
                    } else {
                        movie.getCategories().add(category);
                        moviesMap.put(movie.getId(), movie);
                    }
                }
            }
        } catch (SQLException | MoviesException e) {
            System.out.println(e.getMessage());
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED);
        }
        return moviesMap;
    }

    public Map<Integer, Movie> getMovies(int categoryId) throws MoviesException {
        return retrieveMovies(categoryId);
    }


    public void testConnection() {
        String sql = "SELECT * FROM Movie";
        Movie movie;
        try (Connection connection = CONNECTION_MANAGER.getConnection()) {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double rating = rs.getDouble(3);
                String filel = rs.getString(4);
                Date date = rs.getDate(5);
                double personalRating = rs.getDouble(6);
                movie = new Movie(id, name, rating, filel, date, personalRating, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MoviesException e) {
            throw new RuntimeException(e);
        }
    }
}
