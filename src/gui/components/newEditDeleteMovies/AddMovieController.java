package gui.components.newEditDeleteMovies;

import exceptions.MoviesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import utility.ExceptionHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMovieController extends NewEditController implements Initializable {
    private MovieModel model;
    @FXML
    private HBox genresContainer;
    @FXML
    private GenreView genreList;

    public void openFileChoser(ActionEvent event) {
    }

    public void addNewSong(ActionEvent event) {
        genreList.getSelectedGenres().forEach(System.out::println);
    }

    public void cancelAddNewSong(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            genreList = new GenreView();
            genreList.setGenres(getMovieModel().getGenres());
            genresContainer.getChildren().add(genreList);
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getExceptionsMessages(),"Genres error");
        }
    }
}
