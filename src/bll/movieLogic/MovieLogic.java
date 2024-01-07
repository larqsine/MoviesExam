package bll.movieLogic;

import be.Movie;
import dal.IMovieDao;
import dal.IMovieReader;
import dal.MovieDao;
import dal.MovieReader;
import exceptions.MoviesException;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieLogic implements MovieLogicAPI {
    private final IMovieDao movieDao;
    private final IMovieReader movieReader;

    public MovieLogic() {
        movieDao = new MovieDao();
        movieReader = new MovieReader();
    }

    public Map<Integer, Movie> getMovies(int categoryId) throws MoviesException {
        return movieDao.getMovies(categoryId);
    }

    @Override
    public Media retrieveMedia(int movieId, Map<Integer, Movie> movieObjects) throws MoviesException {
        Movie movie = movieObjects.get(movieId);
        if (movie == null) {
            throw new MoviesException("Movie with ID " + movieId + " not found.");
        }
        return getMediaFromLocal(movie.getFileLink());
    }

    @Override
    public Media retrieveInitialDefaultMedia() throws MoviesException {
        return this.movieReader.getInitialMedia();
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

    public Media getMediaFromLocal(String path) throws MoviesException {
        return movieReader.getMedia(path);
    }

}

