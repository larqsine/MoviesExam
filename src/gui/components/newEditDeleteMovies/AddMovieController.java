package gui.components.newEditDeleteMovies;

import be.Movie;
import exceptions.MoviesException;
import gui.components.newEditDeleteMovies.genreView.GenreView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utility.ExceptionHandler;
import utility.ExceptionsMessages;
import utility.MovieFormat;
import utility.Utility;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddMovieController extends NewEditController implements Initializable {
    @FXML
    private TextField movieTitle;
    @FXML
    private Button createButton;
    @FXML
    private TextField fileLocation;
    private MovieModel model;
    @FXML
    private HBox genresContainer;
    @FXML
    private GenreView genreList;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            model = MovieModel.getInstance();
            genreList = new GenreView();
            genreList.setGenres(model.getGenres());
            genresContainer.getChildren().add(genreList);
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getExceptionsMessages(), "Add movie error");
        }


    }


//
//private boolean checkIfCategorySelected(SimpleIntegerProperty catId){
//   return   catId.getValue()!=0;
//}
//
//private void disableButton(Button button,boolean isCategoryOpened){
//   button.setDisable(isCategoryOpened);
//}
//
//private void displayInfoMessage(boolean isCategorySelected){
//        if(!isCategorySelected){
////            initiateInfoAlert();
//        }
//}


    @Override
    public void cancelAddEditMovie(ActionEvent event) {
        System.out.println("sads");
    }

    @Override
    public void saveAddEditMovie(ActionEvent event) {
        String title = movieTitle.getText();
        String path = fileLocation.getText();
        if (validateInputs(title, path, this.model)) {
            return;
        }
        List<String> genres = this.genreList.getSelectedGenres();
        try {
            boolean isSuccess = model.saveMovie(title, path, genres, this.getOpenedCategory());
            if (!isSuccess) {
                // Show an error alert if the operation was unsuccessful
                ExceptionHandler.displayErrorAlert(ExceptionsMessages.DB_UNSUCCESFULL.getValue(), "Unsuccessful operation");
            }

            closeStage(Utility.getCurrentStage(event));
        } catch (MoviesException e) {
            // Show an error alert if an exception occurs
            ExceptionHandler.displayErrorAlert(ExceptionsMessages.DB_UNSUCCESFULL.getValue(), "Unsuccessful operation");
            closeStage(Utility.getCurrentStage(event));
        }
    }


    @Override
    public void openFileChooser(ActionEvent event) {
        Stage newSongStage = Utility.getCurrentStage(event);
        File selectedFile = getFileChooser().showOpenDialog(newSongStage);
        if (selectedFile != null) {
            try {
                MovieFormat movieformat = setMovieFormat(selectedFile);
                this.fileLocation.setText(selectedFile.getPath());
            } catch (MoviesException e) {
                ExceptionHandler.displayErrorAndWait(e.getMessage(), "Format unsupported");
            }
        }
    }


    public void getCurrentOpenedCategory(int currentOpenedCategory) {
        this.setOpenedCategory(currentOpenedCategory);
    }

    private MovieFormat setMovieFormat(File file) throws MoviesException {
        return model.getFormat(file.getName());
    }
}
