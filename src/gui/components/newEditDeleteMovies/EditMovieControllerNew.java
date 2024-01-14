package gui.components.newEditDeleteMovies;

import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.components.listeners.MovieReloadable;
import gui.components.newEditDeleteMovies.genreView.GenreView;
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

public class EditMovieControllerNew extends NewEditController implements Initializable {
    private MovieModel model;
    @FXML
    private TextField fileLocation;
    @FXML
    private GenreView genreList;

    @FXML
    private HBox genresContainer;
    @Override
    public void cancelAddEditMovie(ActionEvent event) {
        this.closeStage(Utility.getCurrentStage(event));
    }

    @Override
    public void saveAddEditMovie(ActionEvent event) {

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

    public void setModel(MainModel model){
        MovieReloadable movieReloadable = new MovieReloader(model);
        this.setReloadableController(movieReloadable);
    }

    public void getCurrentOpenedCategory(int currentOpenedCategory) {
        this.setOpenedCategory(currentOpenedCategory);
    }
    private MovieFormat setMovieFormat(File file) throws MoviesException {
        return model.getFormat(file.getName());
    }
}
