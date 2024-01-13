package dal;
import be.Movie;
import exceptions.MoviesException;

import java.util.List;
import java.util.Map;

public interface IMovieDao {
    Map<Integer,Movie> getMovies(int categoryId) throws MoviesException;
    void testConnection();

    void createMovie(Movie movie, int categoryId);
//used to insert genres into the database
    //    void insertGenres(List<String> genres);
}
