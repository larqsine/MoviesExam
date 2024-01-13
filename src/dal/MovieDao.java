package dal;
import be.Category;
import be.Movie;
import exceptions.MoviesException;
import utility.ExceptionsMessages;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class MovieDao implements IMovieDao {
    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();

    /**
     * retrieves the movie data from the database
     */
    private Map<Integer, Movie> retrieveMovies(int categoryId) throws MoviesException {
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
                    moviesMap.put(movie.getId(), movie);
                }
            }
        } catch (SQLException | MoviesException e) {
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED);
        }
        Map<Integer, List<Category>> moviesGenre = fetchAllCategoriesForMovies(moviesMap.keySet());
        for (Map.Entry<Integer, Movie> entry : moviesMap.entrySet()) {
            movie = entry.getValue();
            List<Category> categories = moviesGenre.getOrDefault(movie.getId(), Collections.emptyList());
            movie.setCategories(categories);
        }
        return moviesMap;
    }

    public Map<Integer, Movie> getMovies(int categoryId) throws MoviesException {
        return retrieveMovies(categoryId);
    }


    private Map<Integer, List<Category>> fetchAllCategoriesForMovies(Set<Integer> movieIds) throws MoviesException {
        if (movieIds.isEmpty()) {
            return Collections.emptyMap();
        }

        String inSql = movieIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String sql = "SELECT cm.MovieId, c.id, c.name FROM CatMovie cm JOIN Category c ON c.id = cm.CategoryId WHERE cm.MovieId IN (" + inSql + ")";

        Map<Integer, List<Category>> categoriesMap = new HashMap<>();
        try (Connection conn = CONNECTION_MANAGER.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int movieId = rs.getInt(1);
                    int categoryId = rs.getInt(2);
                    String categoryName = rs.getString(3);

                    Category category = new Category(categoryId, categoryName);
                    categoriesMap.computeIfAbsent(movieId, k -> new ArrayList<>()).add(category);
                }
            }
        } catch (SQLException e) {
            throw new MoviesException("Error fetching categories", e);
        }
        return categoriesMap;
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
