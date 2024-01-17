package gui.components.newEditDeleteMovies;

import be.Movie;
import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.components.listeners.MovieReloadable;
import gui.components.newEditDeleteMovies.genreView.MultipleChoiceView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utility.ExceptionHandler;
import utility.MovieFormat;
import utility.Utility;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditMovieController extends NewEditController implements Initializable {
    private MovieModel model;
    @FXML
    private TextField fileLocation;
    @FXML
    private MultipleChoiceView genreList;
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
    if (validateRating(personalRating)) {
        rating = Double.parseDouble(personalRating.getText());
        System.out.println(rating);


}
    }

    @Override
    public void openFileChooser(ActionEvent event) {
        Stage newMovieStage = Utility.getCurrentStage(event);
        File selectedFile = getFileChooser().showOpenDialog(newMovieStage);
        if (selectedFile != null) {
            try {
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
            genreList = new MultipleChoiceView();
            genreList.setElements(model.getGenres());
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

    //    private boolean isValidTitle(){
//        String title = getMovieTitle().getText();
//        return !getMovieModel().checkTitle(title);
//    }
//
//
    public void setTextFieldText(Movie movie) {
        model.setCurrentSelectedMovie(movie);
        this.movieTitle.setText(movie.getName());
    }

    public void setPersonalRating(double rating){
        this.personalRating.setText(String.valueOf(rating));
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
