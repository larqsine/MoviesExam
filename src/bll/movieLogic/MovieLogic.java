package bll.movieLogic;

import be.Movie;
import dal.IMovieDao;
import dal.IMovieReader;
import dal.MovieDao;
import dal.MovieReader;
import exceptions.MoviesException;
import javafx.scene.media.Media;

import java.util.*;

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

    @Override
    public boolean checkIfMovieIsPlaying(int selectedMovie, int currentMovieId) {
        return selectedMovie==currentMovieId;
    }


    public Map<Integer, Movie> applyFilter(String filter, Map<Integer, Movie> toFilter) {
        Map<Integer, Movie> moviesFiltered = new HashMap<>();
        if (filter == null || filter.isEmpty()) {
            return toFilter;
        }
        String filterLower = filter.toLowerCase();
        Set<Integer> keys = toFilter.keySet();
        for (Integer key : keys) {
            String name = toFilter.get(key).getName().toLowerCase();
            if (name.contains(filterLower)) {
                moviesFiltered.put(key, toFilter.get(key));
            }

        }
        return moviesFiltered;
    }

    public Media getMediaFromLocal(String path) throws MoviesException {
        return movieReader.getMedia(path);
    }





    //used to insert genres into the database
//   public void insertGenres(List<String> genres){
//        this.movieDao.insertGenres(genres);
//   }
}

