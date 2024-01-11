package gui.components.newEditDeleteMovies;

import be.Movie;
import exceptions.MoviesException;
import gui.components.confirmationWindow.ConfirmationWindow;
import gui.components.listeners.ConfirmationController;
import gui.components.listeners.MediaViewReloader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import utility.ExceptionHandler;
import utility.Titles;
import utility.InformationalMessages;


import java.net.URL;
import java.util.ResourceBundle;

public class DeleteMovieController implements ConfirmationController, Initializable {
    private MovieModel movieModel;
    private HBox confirmationWindow;
    private Movie movieToDelete;

    @Override
    public void confirmationEventHandler(boolean confirmation) {
        boolean deleted;
        if (confirmation) {
            deleted = movieModel.deleteMovie(this.movieToDelete);
            if (deleted) {
                Platform.runLater(() -> {
                });
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieModel = MovieModel.getInstance();

        if (movieModel != null) {
            ConfirmationWindow confirmationView = new ConfirmationWindow();
            confirmationWindow = confirmationView.getConfirmationWindow();
            initializeConfirmationWindow(confirmationView, this);
        }
    }

    @FXML
    public void deleteSelectedMovie(ActionEvent event) {
        boolean deleted;
        deleted = movieModel.deleteMovie(this.movieToDelete);
        if (deleted) {
            Platform.runLater(() -> {
            });
        }
    }

    public void getMovieToDelete(Movie movie) {
        this.movieToDelete = movie;
    }

    private void initializeConfirmationWindow(ConfirmationWindow confirmationWindow, ConfirmationController confirmationController) {
        confirmationWindow.setConfirmationController(confirmationController);
        confirmationWindow.setOperationTitle(Titles.DELETE_MOVIE.getValue());
        String movieName = "\"" + this.movieToDelete.getName() + "\" ?";
        confirmationWindow.setOperationInformation(InformationalMessages.DELETE_MOVIE_QUESTION.getValue() + movieName);
    }

    public void setReloadable(MediaViewReloader mediaViewReloader) {
    }

    public HBox getConfirmationWindow() {
        return confirmationWindow;
    }
}