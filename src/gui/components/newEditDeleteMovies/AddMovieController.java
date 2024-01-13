package gui.components.newEditDeleteMovies;

import exceptions.MoviesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import utility.ExceptionHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMovieController extends NewEditController implements Initializable {
    public TextField movieTitle;
    private MovieModel model;
    @FXML
    private HBox genresContainer;
    @FXML
    private GenreView genreList;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = getMovieModel();
        genreList = new GenreView();
        genreList.setGenres(model.getGenres());
        genresContainer.getChildren().add(genreList);
    }


@Override
    public void cancelAddEditMovie(ActionEvent event) {
        System.out.println("sads");
    }


@Override
    public void saveAddEditMovie(ActionEvent event) {
        System.out.println("asd");
    }


@Override
    public void openFileChooser(ActionEvent event) {
        System.out.println("asd");
    }


}
