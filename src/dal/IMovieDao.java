package dal;
import be.Category;
import be.Movie;
import exceptions.MoviesException;

import java.util.List;
import java.util.Map;

public interface IMovieDao {
    Map<Integer,Movie> getMovies(int categoryId) throws MoviesException;
    void testConnection();

    boolean createMovie(Movie movie, List<Category> categories) throws MoviesException;

    boolean updateMovie(Movie movie) throws MoviesException;

    boolean deleteMovieFromLocalAndDB(Movie movie) throws MoviesException;

    boolean deleteMovieFromDB(Movie movie) throws MoviesException;

    boolean deleteMovieFromCategory(Movie movie , Category category) throws MoviesException;


}
