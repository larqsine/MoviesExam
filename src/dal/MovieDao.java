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
            String sql = "SELECT m.id,m.name, m.rating, m.filelink, m.lastview, m.personalRating FROM CatMovie cm " +
                    "JOIN Movie m  on m.id = cm.MovieId " +
                    "JOIN Category c on c.id = cm.CategoryId " +
                    "WHERE c.id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int movieId = rs.getInt(1);
                    String movieName = rs.getString(2);
                    double rating = rs.getDouble(3);
                    String fileLink = rs.getString(4);
                    Date lastView = rs.getDate(5);
                    double personalRating = rs.getDouble(6);
                    movie = new Movie(movieId, movieName, rating, fileLink, lastView, personalRating, null);
                    List<Category> categories = getMovieCategories(movie.getId());
                    movie.setCategories(categories);
                    moviesMap.put(movie.getId(), movie);

                }
            }
        } catch (SQLException | MoviesException e) {
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED);
        }
        return moviesMap;
    }

    public Map<Integer, Movie> getMovies(int categoryId) throws MoviesException {
        return retrieveMovies(categoryId);
    }


    private List<Category> getMovieCategories(int movieId) {
        String sql = "SELECT c.id,c.name FROM CatMovie cm JOIN Category c on c.id=cm.CategoryId JOIN Movie m on m.id=cm.MovieId WHERE m.id=?";
        List<Category> movieCategories = new ArrayList<>();
        Category category;
        try (Connection connection = CONNECTION_MANAGER.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int catId = rs.getInt(1);
                    String name = rs.getString(2);
                    category = new Category(catId, name);
                    movieCategories.add(category);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MoviesException e) {
            throw new RuntimeException(e);
        }
        return movieCategories;
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
