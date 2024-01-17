package dal;
import be.Movie;
import exceptions.MoviesException;

import java.util.List;
import java.util.Map;

public interface IMovieDao {
    Map<Integer,Movie> getMovies(int categoryId) throws MoviesException;
    void testConnection();

    boolean createMovie(Movie movie, int categoryId) throws MoviesException;

    boolean updateMovie(Movie movie, String movieTitle) throws MoviesException;

    boolean deleteMovie(Movie movie) throws MoviesException;
//used to insert genres into the database
    //    void insertGenres(List<String> genres);

}
