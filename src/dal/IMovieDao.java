package dal;
import be.Movie;
import exceptions.MoviesException;
import java.util.Map;

public interface IMovieDao {
    Map<Integer,Movie> getMovies() throws MoviesException;
    void testConnection();
}
