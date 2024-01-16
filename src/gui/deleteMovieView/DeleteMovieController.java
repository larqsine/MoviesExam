package gui.deleteMovieView;

import be.Movie;
import exceptions.MoviesException;
import gui.components.confirmationWindow.ConfirmationWindow;
import gui.components.listeners.ConfirmationController;
import gui.components.listeners.MovieReloadable;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import utility.ExceptionHandler;
import utility.InformationalMessages;
import utility.Titles;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteMovieController implements ConfirmationController, Initializable {
    private DeleteModel deleteModel;
    private VBox confirmationWindow;
    private Movie movieToDelete;
    private MovieReloadable reloadable;
    @Override
    public void confirmationEventHandler(boolean confirmation) {
        if (confirmation) {
            try {
                deleteModel.deleteMovie(movieToDelete);
                String message = movieToDelete.getName() + " " + InformationalMessages.DELETE_SUCCEEDED.getValue();
                Platform.runLater(() -> {
                    ExceptionHandler.displayInformationAlert(message,"Delete operation successful");
                });
                reloadSongs();
            } catch (MoviesException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void reloadSongs() throws MoviesException {
   reloadable.reloadMovies();
    }

    public DeleteMovieController(MovieReloadable reloadable) {
        this.reloadable = reloadable;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            deleteModel = DeleteModel.getInstance();
            ConfirmationWindow confirmationView = new ConfirmationWindow();
            if (confirmationView.getConfirmationWindow() == null) {
                return;
            }
            confirmationWindow = confirmationView.getConfirmationWindow();
            initializeConfirmationWindow(confirmationView, this);
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage(),"Alooo");
        }

//        if (deleteModel != null) {
//            ConfirmationWindow confirmationView = new ConfirmationWindow();
//            if (confirmationView.getConfirmationWindow() == null) {
//                return;
//            }
//            confirmationWindow = confirmationView.getConfirmationWindow();
//            initializeConfirmationWindow(confirmationView, this);
//        }
    }

    public void setMovieToDelete(Movie movieToDelete) {
        this.movieToDelete = movieToDelete;
    }

    private void initializeConfirmationWindow(ConfirmationWindow confirmationWindow, ConfirmationController confirmationController) {
        confirmationWindow.setConfirmationController(confirmationController);
        confirmationWindow.setOperationTitle(Titles.DELETE_MOVIE.getValue());
        String message = InformationalMessages.DELETE_QUESTION.getValue() + "\n";
        String songName = "\"" + movieToDelete.getName() + "\"?";
        confirmationWindow.setOperationInformation(message + songName);
    }

    public void setReloadable(MovieReloadable reloadable) {
        this.reloadable = reloadable;
    }

    public VBox getConfirmationWindow() {
        return confirmationWindow;
    }

}
