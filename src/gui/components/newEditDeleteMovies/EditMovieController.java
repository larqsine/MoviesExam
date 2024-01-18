package gui.components.newEditDeleteMovies;
import be.Movie;
import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.components.listeners.MovieReloadable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.ExceptionHandler;
import utility.ExceptionsMessages;
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
    private TextField movieTitle;
    @FXML
    private TextField personalRating;
    private Movie movie;


    @Override
    public void cancelAddEditMovie(ActionEvent event) {
        this.closeStage(Utility.getCurrentStage(event));
    }

    @Override
    public void saveAddEditMovie(ActionEvent event) {
        double personalRatingValue = 0;
        String newTitle = "";
        String newFileLocation = "";
        if(!validateRatingFormat(personalRating)&& !validateInputs(movieTitle.getText(), fileLocation.getText(), this.model)){
            return;
        }
        personalRatingValue = Double.parseDouble(personalRating.getText());
        if(!validateRatingValue(personalRatingValue)){
            return;
        }



            newTitle = movieTitle.getText();
            newFileLocation = fileLocation.getText();
        try {
            boolean isSuccess = model.updateMovie(movie.getId(),newTitle,movie.getRating(),newFileLocation,movie.getLastView(),personalRatingValue,movie.getGenres());
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
            setTextMovieTitle(this.movie.getName());
            setFileLocation(this.movie.getFileLink());
            setPersonalRating(this.movie.getPersonalRating());
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getExceptionsMessages(), "Add movie error");
        }
    }

    public void setModel(MainModel model) {
        MovieReloadable movieReloadable = new MovieReloader(model);
        this.setReloadableController(movieReloadable);
    }
    private MovieFormat setMovieFormat(File file) throws MoviesException {
        return model.getFormat(file.getName());
    }


    public void setTextMovieTitle(String text) {
        movieTitle.setText(text);
    }

    public void setPersonalRating(double rating) {
        this.personalRating.setText(String.valueOf(rating));
    }

    public void setFileLocation(String location) {
        this.fileLocation.setText(location);
    }


    private boolean validateRatingFormat(TextField textField) {
        try {
            Double.parseDouble(personalRating.getText());
            return true;
        } catch (NumberFormatException ex) {
            ExceptionHandler.displayInformationAlert(ex.getMessage(), "Wrong format ");
            return false;
        }
    }

    private  boolean validateRatingValue(double value){
       if(model.validateRatingValue(value)){
           return true;
       }else {
           ExceptionHandler.displayInformationAlert("Value off rating must be between 0 and 10","Wrong value");
           return false;
       }
    }

    public void getMovieToEdit(Movie selectedMovie) {
        System.out.println(selectedMovie);
        this.movie = selectedMovie;
    }
}
