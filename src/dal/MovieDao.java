package dal;
import be.Category;
import be.Movie;
import exceptions.MoviesException;
import utility.ExceptionsMessages;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MovieDao  implements IMovieDao{
    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();

    private List<Movie> retrieveMovies() throws MoviesException {
        Map<Integer,Movie> moviesMap = new HashMap<>();
        List<Movie> movies = new ArrayList<>();
        Movie movie ;
        Category category;
        try (Connection conn = CONNECTION_MANAGER.getConnection()) {
            String sql = "SELECT m.id,m.name, m.rating, m.filelink, m.lastview, m.personalRating, c.id, c.name" +
                    " FROM CatMovie cm" +
                    " JOIN Movie m  on m.id = cm.MovieId" +
                    " JOIN Category c on c.id = cm.CategoryId";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                int movieId = rs.getInt(1);
                String movieName = rs.getString(2);
                double rating = rs.getDouble(3);
                String fileLink = rs.getString(4);
                Date lastView = rs.getDate(5);
                double personalRating = rs.getDouble(6);
                int categoryId = rs.getInt(7);
                String categoryName = rs.getString(8);
               movie = new Movie(movieId,movieName,rating,fileLink,lastView,personalRating,null);
                category = new Category(categoryId,categoryName);
                System.out.println(movie);
                if (moviesMap.containsKey(movie.getId())) {
                    moviesMap.get(movie.getId()).getCategories().add(category);
                } else {
                    movie.getCategories().add(category);
                    moviesMap.put(movie.getId(), movie);
                }
            }
        } catch (SQLException | MoviesException e) {
            throw new MoviesException(ExceptionsMessages.READING_FROMDB_FAILED);
        }
        moviesMap.keySet().forEach(elem -> movies.add(moviesMap.get(elem)));
        return movies;
    }
    public List<Movie> getMovies() throws MoviesException {

        return retrieveMovies();
    }


    public void testConnection(){
        String sql = "SELECT * FROM Movie";
        Movie movie;
        try(Connection connection = CONNECTION_MANAGER.getConnection()){
            Statement stm =connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){

                int id= rs.getInt(1);
                String name = rs.getString(2);
                double rating = rs.getDouble(3);
                String filel = rs.getString(4);
                Date date = rs.getDate(5);
                double personalRating = rs.getDouble(6);
                movie = new Movie(id,name,rating,filel,date,personalRating,null);
                System.out.println(movie.getLastView());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MoviesException e) {
            throw new RuntimeException(e);
        }
    }
}
