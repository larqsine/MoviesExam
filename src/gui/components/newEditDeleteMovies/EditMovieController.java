package gui.components.newEditDeleteMovies;

import exceptions.MoviesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import gui.components.listeners.MediaViewReloader;
import utility.ExceptionHandler;
import utility.InformationalMessages;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMovieController implements Initializable, MediaViewReloader {
    private MovieModel movieModel;

    @FXML
    private TextField movieTitle;
    @FXML
    private Label information;
    @FXML
    private Button saveUpdateButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            movieModel = MovieModel.getInstance();
            setOnChangeListener(getMovieTitle(), getInformation());
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getExceptionsMessages(),"Update error");
        }

    }

    @Override
    public MediaViewReloader getReloadable() {
        return null;
    }

    @Override
    public void getUpdatedMedia(MediaPlayer instance) {

    }

    @Override
    public void saveMovieEdit(ActionEvent event) {
        String title = getMovieTitle().getText();
        if (movieModel.checkTitle(title)) {
            showTitleError();
        } else {
            reloadMovies(); // Corrected method name
            closeCurrentStage(event);
        }
    }

    @Override
    public void cancelMovieEdit(ActionEvent event) {
        closeCurrentStage(event);
    }

    public void setOnChangeListener(TextField textField, Label label) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (label.isVisible()) {
                label.setVisible(false);
            }
        });
    }

    public void showTitleError() {
        getInformation().setText(InformationalMessages.NO_EMPTY_TITLE.getValue());
        getInformation().setVisible(true);
    }

    public Stage getCurrentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public MovieModel getMovieModel() throws MoviesException {
        return MovieModel.getInstance();
    }

    public TextField getMovieTitle() {
        return movieTitle;
    }

    public Label getInformation() {
        return information;
    }

    public Button getSaveUpdateButton() {
        return saveUpdateButton;
    }

    @Override
    public void reloadMovies() {
    }


    private void closeCurrentStage(ActionEvent event) {
        getCurrentStage(event).close();
    }
}
