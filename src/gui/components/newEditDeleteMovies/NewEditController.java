package gui.components.newEditDeleteMovies;

import exceptions.MoviesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import gui.components.listeners.MediaViewReloader;
import utility.ExceptionHandler;
import utility.InformationalMessages;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class NewEditController  {
    private final Alert alertWindowOperation = new Alert(Alert.AlertType.NONE);
    private  MediaViewReloader reloadable ;
    private final FileChooser fileChooser = new FileChooser();



    /**
     * Abstract method to cancel movie creation/editing.
     *
     * @param event The ActionEvent that triggered the cancel operation.
     */
    public abstract void cancelAddEditMovie(ActionEvent event);

    /**
     * Abstract method to save/update a movie.
     *
     * @param event The ActionEvent that triggered the save/update.
     */
    public abstract void saveAddEditMovie(ActionEvent event);


    public abstract void openFileChooser(ActionEvent event);


    public FileChooser getFileChooser() {
        setDefaultExtensions();
        return fileChooser;
    }

    private void setDefaultExtensions() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Mp4 Files", "*.mp4")
                , new FileChooser.ExtensionFilter("Mpeg4 Files", "*.mpeg4")
        );
    }


    public void setReloadableController(MediaViewReloader reloadable) {
        this.reloadable = reloadable;
    }

    public MediaViewReloader getReloadableController() {
        return reloadable;
    }

    public void initiateInfoAlert(Stage newSongStage, String message) {
        alertWindowOperation.setAlertType(Alert.AlertType.INFORMATION);
        alertWindowOperation.setX(newSongStage.getX());
        alertWindowOperation.setY(newSongStage.getY());
        if (message == null) {
            alertWindowOperation.setContentText(InformationalMessages.NO_EMPTY_INPUT.getValue());
        } else {
            alertWindowOperation.setContentText(message);
        }
        alertWindowOperation.showAndWait();
    }

    public void initiateErrorAlert(MoviesException e, Stage newSongStage) {
        alertWindowOperation.setAlertType(Alert.AlertType.ERROR);
        alertWindowOperation.setX(newSongStage.getX());
        alertWindowOperation.setY(newSongStage.getY());
        alertWindowOperation.setContentText(e.getMessage());
        alertWindowOperation.showAndWait();
    }

    public boolean validateInputs(String title , String path, Stage stage, MovieModel movieModel) {
//        if (movieModel.areTitleOrPathEmpty(title, path)) {
//            initiateInfoAlert(stage, null);
//            return true;
//        }
//        if (!movieModel.checkIfFileExists(path)) {
//            initiateInfoAlert(stage, InformationalMessages.NO_FILE.getValue());
//            return true;
//        }
        return false;
    }










//    Salma code
//
//    @Override
//    public MediaViewReloader getReloadable() {
//        return null;
//    }
//
//    @Override
//    public void getUpdatedMedia(MediaPlayer instance) {
//
//    }
//
//    @Override
//    public void saveMovieEdit(ActionEvent event) {
//        String title = getMovieTitle().getText();
//        if (movieModel.checkTitle(title)) {
//            showTitleError();
//        } else {
//            reloadMovies();
//            closeCurrentStage(event);
//        }
//    }
//
//    public void closeCurrentStage(ActionEvent event) {
//    }
//
//    @Override
//    public void cancelMovieEdit(ActionEvent event) {
//        closeCurrentStage(event);
//    }
//
    public void setOnChangeListener(TextField textField, Label label) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (label.isVisible()) {
                label.setVisible(false);
            }
        });
    }
//
//    public void showTitleError() {
//        getInformation().setText(InformationalMessages.NO_EMPTY_TITLE.getValue());
//        getInformation().setVisible(true);
//    }
//
//    public Stage getCurrentStage(ActionEvent event) {
//        return (Stage) ((Node) event.getSource()).getScene().getWindow();
//    }
//



//
//    public TextField getMovieTitle() {
//        return movieTitle;
//    }
//
//    public Label getInformation() {
//        return information;
//    }
//
//    public Button getSaveUpdateButton() {
//        return saveUpdateButton;
//    }
//
//    @Override
//    public void reloadMovies() {
//
//    }


}
