package gui.MainView;

import be.Category;
import be.Movie;
import bll.CategoryLogic;
import bll.CategoryLogicAPI;
import bll.movieLogic.MovieLogic;
import bll.movieLogic.MovieLogicAPI;
import exceptions.MoviesException;
import gui.components.listeners.PlaybackObserver;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import utility.PlayButtonGraphic;

import java.util.ArrayList;
import java.util.List;
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
    private final IntegerProperty currentMovieSelected = new SimpleIntegerProperty();
    private final SimpleStringProperty playButtonValue = new SimpleStringProperty(PlayButtonGraphic.PLAY.getValue());
    private final SimpleStringProperty playButtonFromTableId = new SimpleStringProperty();
    private final List<PlaybackObserver> playbackObservers;


    //    used to attach change listeners to the play operation
    private SimpleBooleanProperty observablePlayProperty = new SimpleBooleanProperty(false);

    /**
     * controls the state off the  play button
     */
    private final SimpleBooleanProperty playButtonState = new SimpleBooleanProperty(false);


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
        this.playbackObservers = new ArrayList<>();
        initializeCategories();
        addChangeListener(observablePlayProperty);
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


    /**
     * retrieve the media from local to be played
     */
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

    public void reloadCategories() throws MoviesException {
        initializeCategories();
    }

    public void resetFilter() {
        this.movies.setAll(movieObjects.keySet().stream().map(elem -> movieObjects.get(elem)).toList());
    }

    public void applyFilter(String filter) {
        Map<Integer, Movie> filteredMovies = movieLogic.applyFilter(filter, movieObjects);
        this.movies.setAll(filteredMovies.keySet().stream().map(elem -> movieObjects.get(elem)).toList());
    }

    /**
     * set the opened category id,  to be used for movie insertion into the database
     */
    public int getCurrentOpenedCategory() {
        return currentOpenedCategory.get();
    }

    /**
     * set the opened category id,  to be used for movie insertion into the database
     */
    public SimpleIntegerProperty currentOpenedCategoryProperty() {
        return currentOpenedCategory;
    }

    /**
     * set the opened category id,  to be used for movie insertion into the database
     */
    public void setCurrentOpenedCategory(int currentOpenedCategory) {
        this.currentOpenedCategory.set(currentOpenedCategory);
    }

    public void reloadMoviesFromDB() throws MoviesException {
        this.setMovieObservableList(this.currentOpenedCategory.get());
    }


//    maybe remove them , i need to try a different approach


    /**
     * used to control the playButton graphics
     */
    public String getPlayButtonValue() {
        return playButtonValue.get();
    }

    /**
     * used to control the playButton graphics
     */
    public SimpleStringProperty playButtonValueProperty() {
        return playButtonValue;
    }

    /**
     * used to control the playButton graphics
     */
    public void setPlayButtonValue(String playButtonValue) {
        this.playButtonValue.set(playButtonValue);
    }

    /**
     * used to control the play button state from the table
     */
    public String getPlayButtonFromTableId() {
        return playButtonFromTableId.get();
    }

    /**
     * used to control the play button state from the table
     */
    public SimpleStringProperty playButtonFromTableIdProperty() {
        return playButtonFromTableId;
    }

    /**
     * used to control the play button state from the table
     */
    public void setPlayButtonFromTableId(String playButtonFromTableId) {
        this.playButtonFromTableId.set(playButtonFromTableId);
    }

    /**
     * control the button state maybe remove them after implementing observable design
     */
    public boolean getPlayButtonState() {
        return playButtonState.get();
    }

    public SimpleBooleanProperty playButtonStateProperty() {
        return playButtonState;
    }

    public void setPlayButtonState(boolean playButtonState) {
        this.playButtonState.set(playButtonState);
    }


    //    the new approach with the observable design pattern
    public void addObserver(PlaybackObserver playbackObserver) {
        playbackObservers.add(playbackObserver);
    }

    private void addChangeListener(SimpleBooleanProperty booleanProperty) {
        booleanProperty.addListener((observable, oldValue, newValue) -> {
            for (PlaybackObserver observer : playbackObservers) {
                observer.playbackChange(newValue);
            }
        });
    }

    public boolean getObservablePlayPropertyValue() {
        return observablePlayProperty.get();
    }

    public SimpleBooleanProperty getObservablePlayPropertyProperty() {
        return observablePlayProperty;
    }

    public void setObservablePlayPropertyValue(boolean observablePlayProperty) {
        System.out.println(observablePlayProperty + "from model");
        this.observablePlayProperty.set(observablePlayProperty);
    }

}





