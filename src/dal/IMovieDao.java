package dal;
import be.Movie;
import exceptions.MoviesException;

import java.util.List;

public interface IMovieDao {
    List<Movie> getMovies() throws MoviesException;
    void testConnection();
}
