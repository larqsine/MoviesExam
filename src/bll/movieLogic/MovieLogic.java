package bll.movieLogic;

import be.Movie;
import dal.IMovieDao;
import dal.MovieDao;
import exceptions.MoviesException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieLogic implements MovieLogicAPI {
    private final IMovieDao movieDao;

    public MovieLogic() {
        movieDao = new MovieDao();

    }

    public Map<Integer, Movie> getMovies(int categoryId) throws MoviesException {
        return movieDao.getMovies(categoryId);
    }

    public List<Movie> applyFilter(String filter, List<Movie> toFilter) {
        if (filter == null || filter.isEmpty()) {
            return new ArrayList<>(toFilter);
        }
        String filterLower = filter.toLowerCase();

        return toFilter.stream()
                .filter(movie -> (movie.getName() != null && movie.getName().toLowerCase().contains(filterLower)))
                .collect(Collectors.toList());
    }
}

