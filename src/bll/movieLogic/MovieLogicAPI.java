package bll.movieLogic;
import be.Movie;
import exceptions.MoviesException;
import javafx.scene.media.Media;

import java.util.List;
import java.util.Map;

public interface MovieLogicAPI {


    Map<Integer,Movie> applyFilter(String filter, Map<Integer,Movie> movies);
    Map<Integer, Movie> getMovies(int categoryId) throws MoviesException;
    Media retrieveMedia(int movieId, Map<Integer, Movie> movieObjects) throws MoviesException;
    Media retrieveInitialDefaultMedia() throws MoviesException;

    boolean checkIfMovieIsPlaying(int selectedMovie, int currentMovieId);



//used to insert genres into the database
    //    void insertGenres(List<String> genres);
}
