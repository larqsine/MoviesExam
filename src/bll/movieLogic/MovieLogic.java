package bll.movieLogic;

import be.Movie;
import dal.IMovieDao;
import dal.IMovieReader;
import dal.MovieDao;
import dal.MovieReader;
import exceptions.MoviesException;
import gui.components.newEditDeleteMovies.NewEditController;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
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


    public Map<Integer, Movie> applyFilter(String filter, Map<Integer, Movie> toFilter) throws MoviesException {
        Map<Integer, Movie> moviesFiltered = new HashMap<>();
        if (toFilter==null){
            throw  new MoviesException("No Movies Opened");
        }

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


    /**
     * get the next index to be played in the list,if reach the end off the list goes back to 0
     **/
    @Override
    public int processIndexUpp(int indexToCheck, int movieSize) {
        if (indexToCheck < movieSize - 1) {
            return indexToCheck + 1;
        }
        return 0;
    }

    /**
     * get the previous index to be played if it reaches the beginning off the list goes to the end off the list
     **/
    @Override
    public int processIndexDown(int indexToCheck, int moviesSize) {
        if (indexToCheck >= 1) {
            return indexToCheck - 1;
        }
        return moviesSize - 1;
    }

    @Override
    public Media getMediaAtIndex(int index, List<Movie> movies) throws MoviesException {
            String path = movies.get(index).getFileLink();
            return this.movieReader.getMedia(path);
    }

    @Override
    public List<String> oldMovies() throws MoviesException {
     return movieDao.getOldVisualizedMovies();
    }
}

