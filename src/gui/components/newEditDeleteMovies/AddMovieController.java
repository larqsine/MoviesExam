package gui.components.newEditDeleteMovies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMovieController implements Initializable {
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
        model = MovieModel.getInstance();
        genreList = new GenreView();
        genreList.setGenres(model.getGenres());
        genresContainer.getChildren().add(genreList);
    }
}
