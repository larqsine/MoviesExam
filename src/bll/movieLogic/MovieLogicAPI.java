package bll.movieLogic;
import be.Movie;
import exceptions.MoviesException;
import java.util.Map;

public interface MovieLogicAPI {
    Map<Integer, Movie> getMovies(int categoryId) throws MoviesException;
}
