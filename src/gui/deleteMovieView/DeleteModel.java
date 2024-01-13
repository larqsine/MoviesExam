package gui.deleteMovieView;

import be.Movie;
import exceptions.MoviesException;
import gui.components.newEditDeleteMovies.MovieModel;
import utility.ExceptionHandler;
import utility.InformationalMessages;

public class DeleteModel {
    private MovieModel movieModel;
    private static DeleteModel instance;

    private DeleteModel() {
        movieModel = MovieModel.getInstance();
    }

    public static DeleteModel getInstance() {
        if (instance == null) {
            instance = new DeleteModel();
        }
        return instance;
    }

    /**
     * Delete a movie from the database
     *
     * @param movieToDelete The movie to be deleted
     * @return True if the movie was deleted successfully, false otherwise
     */
    public boolean deleteMovie(Movie movieToDelete) {
        return movieModel.deleteMovie(movieToDelete);
    }
}
