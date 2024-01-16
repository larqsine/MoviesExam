package gui.deleteMovieView;

import be.Movie;
import bll.movieLogic.MovieCreation;
import exceptions.MoviesException;
import gui.components.newEditDeleteMovies.MovieModel;
import utility.ExceptionHandler;
import utility.InformationalMessages;

public class DeleteModel {
    private MovieCreation movieCreation;
    private static DeleteModel instance;

    private DeleteModel() throws MoviesException {
        this.movieCreation= new MovieCreation();
    }

    public static DeleteModel getInstance() throws MoviesException {
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
    public boolean deleteMovie(Movie movieToDelete) throws MoviesException {
        return movieCreation.deleteMovie(movieToDelete);
    }
}
