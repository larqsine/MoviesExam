package dal;

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
    public boolean createMovie(Movie movie, int categoryId) throws MoviesException {
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
                    insertMovieCategory(categoryId, rs.getInt(1), conn);
                    insertMovieGenres(rs.getInt(1), movie.getGenres(), conn);
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage()); // Log the exception
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback Exception: " + ex.getMessage()); // Log rollback exception
                ExceptionHandler.displayErrorAndWait(ex.getMessage(), "Database operation error");
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Close Connection Exception: " + ex.getMessage()); // Log close exception
                    ExceptionHandler.displayErrorAndWait(ex.getMessage(), "Database operation error");
                }
            }
        }
    }




    private void insertMovieCategory(int categoryId, int movieId, Connection conn) throws MoviesException {
        String sql = "INSERT INTO CatMovie VALUES (?,?)";
        try {

            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                psmt.setInt(1, categoryId);
                psmt.setInt(2, movieId);
                psmt.execute();
            }
        } catch (SQLException e) {
            throw new MoviesException(e);
        }
    }


    private void insertMovieGenres(int movieId, List<Genre> genres, Connection conn) throws MoviesException {
        String sql = "INSERT INTO GenreMovie values(?,?)";
        try {
            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                psmt.setInt(1, movieId);
                for (Genre genre : genres) {
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
    public boolean updateMovie(Movie movie, String movieTitle) throws MoviesException {
        return false;
    }

@Override
    public boolean deleteMovie(Movie movie) throws MoviesException {
    System.out.println(movie.getName());
    System.out.println(movie.getId());
        String sqlDeleteMovie = "DELETE FROM Movie WHERE id=?";
        try (Connection connection = CONNECTION_MANAGER.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement psmtDelete = connection.prepareStatement(sqlDeleteMovie)) {
                psmtDelete.setInt(1, movie.getId());
                int rows =psmtDelete.executeUpdate();
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


//used to insert genres into the database
//    public void insertGenres(List<String> genres){
//        String sql = "INSERT INTO Genre values (?)";
//
//        try(Connection conn =CONNECTION_MANAGER.getConnection()){
//            PreparedStatement psmt = conn.prepareStatement(sql);
//            genres.stream().forEach(elem-> {
//                try {
//                    psmt.setString(1,elem);
//                    psmt.execute();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (MoviesException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
