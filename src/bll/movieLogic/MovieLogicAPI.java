package bll.movieLogic;
import be.Movie;
import exceptions.MoviesException;
import javafx.scene.media.Media;
import java.util.Map;

public interface MovieLogicAPI {
     Map<Integer,Movie> applyFilter(String filter, Map<Integer,Movie> movies);
    Map<Integer, Movie> getMovies(int categoryId) throws MoviesException;


    Media retrieveMedia(int movieId, Map<Integer, Movie> movieObjects) throws MoviesException;
    Media retrieveInitialDefaultMedia() throws MoviesException;
}
