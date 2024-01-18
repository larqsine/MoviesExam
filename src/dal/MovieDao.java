package dal;

import be.Category;
import be.Genre;
import be.Movie;
import exceptions.MoviesException;
import javafx.beans.property.SimpleStringProperty;
import utility.ExceptionHandler;
import utility.ExceptionsMessages;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Date;


public class MovieDao implements IMovieDao {
    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();

    /**
     * retrieves the movie data from the database
     */
    private Map<Integer, Movie> retrieveMovies(int categoryId) throws MoviesException {
        Map<Integer, Movie> moviesMap = new HashMap<>();
        Movie movie;
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
                    moviesMap.put(movie.getId(), movie);
                }
            }
        } catch (SQLException | MoviesException e) {
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED);
        }
        Map<Integer, List<Genre>> moviesGenre = fetchAllGenresForMovies(moviesMap.keySet());
        for (Map.Entry<Integer, Movie> entry : moviesMap.entrySet()) {
            movie = entry.getValue();
            List<Genre> categories = moviesGenre.getOrDefault(movie.getId(), Collections.emptyList());
            movie.setCategories(categories);
        }
        return moviesMap;
    }

    public Map<Integer, Movie> getMovies(int categoryId) throws MoviesException {
        return retrieveMovies(categoryId);
    }


    private Map<Integer, List<Genre>> fetchAllGenresForMovies(Set<Integer> movieIds) throws MoviesException {
        if (movieIds.isEmpty()) {
            return Collections.emptyMap();
        }
        String inSql = movieIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String sql = "SELECT gm.MovieId, g.GenreId, g.name FROM GenreMovie gm JOIN Genre g ON g.GenreId= gm.GenreId WHERE gm.MovieId IN (" + inSql + ")";
        Map<Integer, List<Genre>> genresMap = new HashMap<>();
        try (Connection conn = CONNECTION_MANAGER.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int movieId = rs.getInt(1);
                    int genreId = rs.getInt(2);
                    SimpleStringProperty genreName = getSimpleStringProperty(rs.getString(3));
                    Genre genre = new Genre(genreId, genreName);
                    genresMap.computeIfAbsent(movieId, k -> new ArrayList<>()).add(genre);
                }
            }
        } catch (SQLException e) {
            throw new MoviesException("Error fetching categories", e);
        }
        return genresMap;
    }

    private static SimpleStringProperty getSimpleStringProperty(String name) throws SQLException {
        SimpleStringProperty genreName = new SimpleStringProperty();
        genreName.setValue(name);
        return genreName;
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


    @Override
    public boolean createMovie(Movie movie, List<Category> categories) throws MoviesException {
        System.out.println(movie);
        String sql = "INSERT INTO Movie values(?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = CONNECTION_MANAGER.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement psmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psmt.setString(1, movie.getName());
                psmt.setDouble(2, movie.getRating());
                psmt.setString(3, movie.getFileLink());
                java.sql.Date sqlDate = new java.sql.Date(movie.getLastView().getTime());
                psmt.setDate(4, sqlDate);
                psmt.setDouble(5, movie.getPersonalRating());
                psmt.execute();
                ResultSet rs = psmt.getGeneratedKeys();
                if (rs.next()) {
                    insertMovieCategory(rs.getInt(1), categories, conn);
                    insertMovieGenres(rs.getInt(1), movie.getGenres(), conn);
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback Exception: " + ex.getMessage());
                ExceptionHandler.displayErrorAndWait(ex.getMessage(), "Database operation error");
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Close Connection Exception: " + ex.getMessage());
                    ExceptionHandler.displayErrorAndWait(ex.getMessage(), "Database operation error");
                }
            }
        }
    }


    private void insertMovieCategory(int movieId, List<Category> categories, Connection conn) throws MoviesException {
        String sql = "INSERT INTO CatMovie VALUES (?,?)";
        try {
            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                for (Category category : categories) {
                    psmt.setInt(1, category.getId());
                    psmt.setInt(2, movieId);
                    psmt.addBatch();
                }
                psmt.executeBatch();
            }
        } catch (SQLException e) {
            throw new MoviesException(e);
        }
    }


    private void insertMovieGenres(int movieId, List<Genre> genres, Connection conn) throws MoviesException {
        String sql = "INSERT INTO GenreMovie values(?,?)";
        try {
            try (PreparedStatement psmt = conn.prepareStatement(sql)) {

                for (Genre genre : genres) {
                    psmt.setInt(1, movieId);
                    psmt.setInt(2, genre.getId());
                    psmt.addBatch();
                }
                psmt.executeBatch();
            }
        } catch (SQLException e) {
            throw new MoviesException(e);
        }
    }

    @Override
    public boolean updateMovie(Movie movie) throws MoviesException {
        String sql1 = "UPDATE Movie SET name=?, rating=?, filelink=?,lastview=?, personalRating=? WHERE id=?";
        Connection conn = null;

        try {
            conn = CONNECTION_MANAGER.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psmt = conn.prepareStatement(sql1)) {
                psmt.setString(1, movie.getName());
                psmt.setDouble(2, movie.getRating());
                psmt.setString(3, movie.getFileLink());
                java.sql.Date sqlDate = new java.sql.Date(movie.getLastView().getTime());
                psmt.setDate(4, sqlDate);
                psmt.setDouble(5, movie.getPersonalRating());
                psmt.setInt(6, movie.getId());
                int rowsAffected = psmt.executeUpdate();
                conn.commit();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback Exception: " + ex.getMessage());
                ExceptionHandler.displayErrorAndWait(ex.getMessage(), "Database operation error");
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Close Connection Exception: " + ex.getMessage());
                    ExceptionHandler.displayErrorAndWait(ex.getMessage(), "Database operation error");
                }
            }
        }
    }

    @Override
    public boolean deleteMovieFromLocalAndDB(Movie movie) throws MoviesException {
        String sqlDeleteMovie = "DELETE FROM Movie WHERE id=?";
        try (Connection connection = CONNECTION_MANAGER.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement psmtDelete = connection.prepareStatement(sqlDeleteMovie)) {
                psmtDelete.setInt(1, movie.getId());
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
    }

    @Override
    public boolean deleteMovieFromCategory(Movie movie, Category category) throws MoviesException {
        String sql = "DELETE FROM CatMovie WHERE CategoryId=? AND MovieID=?";
        try (Connection connection = CONNECTION_MANAGER.getConnection()) {
            try (PreparedStatement psmt = connection.prepareStatement(sql)) {
                psmt.setInt(1, category.getId());
                psmt.setInt(2, movie.getId());
                psmt.execute();
                return true;
            }
        } catch (SQLException e) {
            throw new MoviesException(e.getMessage());
        }
    }

    @Override
    public boolean deleteMovieFromDB(Movie movie) throws MoviesException {
        String sqlDeleteMovie = "DELETE FROM Movie WHERE id=?";
        try (Connection connection = CONNECTION_MANAGER.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement psmtDelete = connection.prepareStatement(sqlDeleteMovie)) {
                psmtDelete.setInt(1, movie.getId());
                psmtDelete.execute();
                return true;
            }
        } catch (SQLException e) {
            throw new MoviesException(ExceptionsMessages.NO_DATABASE_CONNECTION, e);
        }
    }
}


