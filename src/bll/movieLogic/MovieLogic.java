package bll.movieLogic;

import be.Movie;
import dal.IMovieDao;
import dal.MovieDao;
import exceptions.MoviesException;

import java.util.Map;

public class MovieLogic implements MovieLogicAPI {
private final IMovieDao movieDao;

    public MovieLogic() {
        movieDao= new MovieDao();

    }

    public Map<Integer, Movie> getMovies(int categoryId) throws MoviesException {
        return movieDao.getMovies(categoryId);
    }
}
