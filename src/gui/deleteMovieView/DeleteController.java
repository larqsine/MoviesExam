package gui.deleteMovieView;

import be.Movie;
import exceptions.MoviesException;
import gui.components.confirmationWindow.ConfirmationWindow;
import gui.components.listeners.ConfirmationController;
import gui.components.listeners.MediaViewReloader;
import gui.components.newEditDeleteMovies.MovieModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import utility.ExceptionHandler;
import utility.InformationalMessages;
import utility.Titles;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteController implements ConfirmationController, Initializable {
    private MovieModel movieModel;
    private VBox confirmationWindow;
    private Movie movieToDelete;
    private MediaViewReloader mediaViewReloader;

//    @Override
//    public void confirmationEventHandler(boolean confirmation) {
//        if (confirmation) {
//            boolean deleted = movieModel.deleteMovie(this.movieToDelete);
//            if (deleted) {
//                String message = movieToDelete.getName() + " " + InformationalMessages.DELETE_SUCCEEDED.getValue();
//                Platform.runLater(() -> {
//                    ExceptionHandler.displayInformationAlert(message,null);
//                });
//                reloadMovies();
//            }
//        }
//    }

    @Override
    public void confirmationEventHandler(boolean confirmation) {

    }

//    @Override
//    public void confirmationEventHandeler(boolean confirmation) {
//
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            movieModel = MovieModel.getInstance();
        } catch (MoviesException e) {
            throw new RuntimeException(e);
        }
        if (movieModel != null) {
            ConfirmationWindow confirmationView = new ConfirmationWindow();
            if (confirmationView.getConfirmationWindow() == null) {
                return;
            }
            confirmationWindow = confirmationView.getConfirmationWindow();
            initializeConfirmationWindow(confirmationView, this);
        }
    }

//    @FXML
//    public void deleteSelectedMovie(ActionEvent event) {
//        boolean deleted = movieModel.deleteMovie(this.movieToDelete);
//        if (deleted) {
//            Platform.runLater(() -> {
//            });
//            reloadMovies();
//        }
//    }

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
        this.mediaViewReloader = mediaViewReloader;
    }

    public VBox getConfirmationWindow() {
        return confirmationWindow;
    }

//    private void reloadMovies() {
//        if (mediaViewReloader != null) {
//            mediaViewReloader.reloadMovies();
//        }
//    }
}