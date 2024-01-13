package gui.components.newEditDeleteMovies;

import be.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.MovieGenre;

import java.util.Arrays;
import java.util.List;

public class MovieModel {

    private final ObservableList<String> genres;

    public ObservableList<String> getGenres() {
        return genres;
    }

    private static MovieModel instance;

    public static MovieModel getInstance() {
        if (instance == null) {
            instance = new MovieModel();
        }
        return instance;
    }

    private MovieModel() {
        this.genres = FXCollections.observableArrayList();
        initializeGenres();
    }

    private void initializeGenres() {
        List<String> genres = Arrays.stream(MovieGenre.values()).map(MovieGenre::getDisplayName).toList();
        this.genres.setAll(genres);
    }


    public boolean deleteMovie(Movie movieToDelete) {
        return false;
    }

    public void cancelUpdateMovie() {
    }

    public void setCurrentSelectedMovie(Movie movie) {
    }

    public boolean updateMovie(String title) {
        return false;
    }

    public boolean checkTitle(String title) {
        return false;
    }
}
