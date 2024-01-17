package gui.components.newEditDeleteMovies;

import be.Movie;
import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.components.listeners.MovieReloadable;
import gui.components.newEditDeleteMovies.genreView.GenreView;
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

public class EditMovieController extends NewEditController implements Initializable {
    public Button createButton;
    private MovieModel model;
    @FXML
    private TextField fileLocation;
    @FXML
    private GenreView genreList;
    @FXML
    private HBox genresContainer;
    @FXML
    private TextField movieTitle;
    @FXML
    private TextField personalRating;


    @Override
    public void cancelAddEditMovie(ActionEvent event) {
        this.closeStage(Utility.getCurrentStage(event));
    }

    @Override
    public void saveAddEditMovie(ActionEvent event) {
        double rating = 0;
        String title = movieTitle.getText();
    if (validateRating(personalRating)) {
        rating = Double.parseDouble(personalRating.getText());
        if (validateInputs(title, String.valueOf(rating), this.model)) {
            return;
        }
        List<String> genres = this.genreList.getSelectedGenres();
        try {
            boolean isSuccess = model.saveMovie(title, String.valueOf(rating), genres, this.getOpenedCategory());
            if (!isSuccess) {
                ExceptionHandler.displayErrorAlert(ExceptionsMessages.DB_UNSUCCESFULL.getValue(), "Unsuccessful operation");
            }
            this.getReloadableController().reloadMovies();
            closeStage(Utility.getCurrentStage(event));
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(ExceptionsMessages.DB_UNSUCCESFULL.getValue(), "Unsuccessful operation");
            closeStage(Utility.getCurrentStage(event));
        }
}
    }

    @Override
    public void openFileChooser(ActionEvent event) {
        Stage newSongStage = Utility.getCurrentStage(event);
        File selectedFile = getFileChooser().showOpenDialog(newSongStage);
        if (selectedFile != null) {
            try {
                System.out.println(selectedFile);
                MovieFormat movieformat = setMovieFormat(selectedFile);
                this.fileLocation.setText(selectedFile.getPath());
            } catch (MoviesException e) {
                ExceptionHandler.displayErrorAndWait(e.getMessage(), "Format unsupported");
            }
        }
    }

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

    public void setModel(MainModel model) {
        MovieReloadable movieReloadable = new MovieReloader(model);
        this.setReloadableController(movieReloadable);
    }

    public void getCurrentOpenedCategory(int currentOpenedCategory) {
        this.setOpenedCategory(currentOpenedCategory);
    }

    private MovieFormat setMovieFormat(File file) throws MoviesException {
        return model.getFormat(file.getName());
    }


    public void setTextMovieTitle(String text) {
        movieTitle.setText(text);
    }

    public void setPersonalRating(double rating){
        this.personalRating.setText(String.valueOf(rating));
    }

    public void setFileLocation(String location){
        this.fileLocation.setText(location);
    }

    public void setMovie(Movie movie){
        this.model.setCurrentSelectedMovie(movie);
    }

    private boolean validateRating(TextField textField) {
        try {
            Double.parseDouble(personalRating.getText());
            return true;
        } catch (NumberFormatException ex) {
            ExceptionHandler.displayInformationAlert(ex.getMessage(), "Wrong format ");
            return false;
        }
    }

}
