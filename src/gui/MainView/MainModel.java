package gui.MainView;

import be.Category;
import be.Movie;
import bll.CategoryLogic;
import bll.CategoryLogicAPI;
import bll.movieLogic.MovieLogic;
import bll.movieLogic.MovieLogicAPI;
import exceptions.MoviesException;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;

import java.awt.*;
import java.util.Map;

public class MainModel {
    private boolean playMusic = false;
    private final CategoryLogicAPI categoryLogic;
    private final MovieLogicAPI movieLogic;
    private static MainModel instance;
    private final ObservableList<Category> categories;
    private final ObservableList<Movie> movies;
    private Map<Integer, Movie> movieObjects;
    private Media currentPlayingMedia;
    private TextField searchValue;
    private IntegerProperty currentMovieSelected = new SimpleIntegerProperty();



    private SimpleIntegerProperty currentOpenedCategory = new SimpleIntegerProperty();
    /**
     * holds the current volume off the appliation
     */
    private DoubleProperty volumeLevel = new SimpleDoubleProperty(100);
    /**
     * controls if the application  volume is mute
     */
    private BooleanProperty isMute = new SimpleBooleanProperty();
    /**
     * Initial default media
     */
    private Media initialDefaultMedia;

    private MainModel() throws MoviesException {
        this.movieLogic = new MovieLogic();
        this.categoryLogic = new CategoryLogic();
        this.categories = FXCollections.observableArrayList();
        this.movies = FXCollections.observableArrayList();
        initializeCategories();
    }

    public static MainModel getInstance() throws MoviesException {
        if (instance == null) {
            instance = new MainModel();
        }
        return instance;
    }

    private void initializeCategories() throws MoviesException {
        this.categories.setAll(this.categoryLogic.getAllCategories());
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public ObservableList<Movie> getMovies() {
        return this.movies;
    }

    public void retrieveMovies(int id) throws MoviesException {
        setMovieObservableList(id);
    }

    private void setMovieObservableList(int categoryId) throws MoviesException {
        movieObjects = movieLogic.getMovies(categoryId);
        this.movies.setAll(movieObjects.keySet().stream().map(elem -> movieObjects.get(elem)).toList());
    }


    public void setPlayMovie(boolean play) {
        this.playMusic = play;
    }

    public void setCurrentPlayingMovie(int movieId) {
        this.currentMovieSelected.set(movieId);
    }

    public Media getNextMovie() {
        return null;
    }

    public Media getPreviousMovie() {
        return null;
    }

    private void getMediaFromLocal(int movieId, Map<Integer, Movie> movies) throws MoviesException {
        this.currentPlayingMedia = movieLogic.retrieveMedia(movieId, this.movieObjects);
    }

    public Media getCurrentMovieToBePlayed() throws MoviesException {
        getMediaFromLocal(this.currentMovieSelected.getValue(), this.movieObjects);
        return this.currentPlayingMedia;
    }

    public boolean isPlayMusic() {
        return this.playMusic;
    }

    public DoubleProperty volumeLevelProperty() {
        return this.volumeLevel;
    }

    public BooleanProperty isMuteProperty() {
        return isMute;
    }

    private void initializeDefaultMedia() throws MoviesException {
        this.initialDefaultMedia = this.movieLogic.retrieveInitialDefaultMedia();
    }

    public Media getInitialDefaultMedia() throws MoviesException {
        initializeDefaultMedia();
        return this.initialDefaultMedia;
    }

    public void reloadSongsFromDB() throws MoviesException {
        initializeCategories();
    }

    public void resetFilter() {
        this.currentMovieSelected.set(0);
        this.searchValue.setText("");
    }

    public void applyFilter(String filter) {
        Map<Integer,Movie> filteredMovies = movieLogic.applyFilter(filter,movieObjects);
        this.movies.setAll(filteredMovies.keySet().stream().map(elem -> movieObjects.get(elem)).toList());
    }

/**
 * set the opened category id,  to be used for movie insertion into the database*/
    public int getCurrentOpenedCategory() {
        return currentOpenedCategory.get();
    }
    /**
     * set the opened category id,  to be used for movie insertion into the database*/
    public SimpleIntegerProperty currentOpenedCategoryProperty() {
        return currentOpenedCategory;
    }
    /**
     * set the opened category id,  to be used for movie insertion into the database*/
    public void setCurrentOpenedCategory(int currentOpenedCategory) {
        this.currentOpenedCategory.set(currentOpenedCategory);
    }
}





