package gui.MainView;
import be.Category;
import be.Movie;
import bll.categoryLogic.CategoryLogic;
import bll.categoryLogic.CategoryLogicAPI;
import bll.movieLogic.MovieCreation;
import bll.movieLogic.MovieLogic;
import bll.movieLogic.MovieLogicAPI;
import exceptions.MoviesException;
import gui.components.listeners.PlaybackObserver;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainModel {

    private final CategoryLogicAPI categoryLogic;
    private final MovieLogicAPI movieLogic;
    private final MovieCreation movieCreation;
    private static MainModel instance;
    private final ObservableList<Category> categories;
    private final ObservableList<Movie> movies;
    private Map<Integer, Movie> movieObjects;
    private Media currentPlayingMedia;
    private final IntegerProperty currentMovieSelected = new SimpleIntegerProperty();
    private final SimpleStringProperty playButtonFromTableId = new SimpleStringProperty();
    /**
     * holds the total duration off the movie
     */
    private final DoubleProperty totalTime = new SimpleDoubleProperty();

    /**
     * Contains all the observers off the play propriety
     */
    private final List<PlaybackObserver> playbackObservers;


    /**
     * used to attach change listeners to the play operation
     */
    private final SimpleBooleanProperty observablePlayProperty = new SimpleBooleanProperty(false);

    private final SimpleIntegerProperty currentOpenedCategory = new SimpleIntegerProperty();

    /**
     * holds the current volume off the appliation
     */
    private final DoubleProperty volumeLevel = new SimpleDoubleProperty(100);

    /**
     * current Index off the movie being played
     */
    private final IntegerProperty currentIndex = new SimpleIntegerProperty(0);


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
        this.movieCreation= new MovieCreation();
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

    public void setCurrentPlayingMovie(int movieId) {
        this.currentMovieSelected.set(movieId);
    }


    /**
     * retrieves the next movie to be played when the next button is played
     */
    public Media getNextMovie() throws MoviesException {
        currentIndex.set(movieLogic.processIndexUpp(currentIndex.get(), movies.size()));
        return movieLogic.getMediaAtIndex(currentIndex.get(), movies);

    }

    /**
     * retrieves the previous movie to be played when the next button is played
     */
    public Media getPreviousMovie() throws MoviesException {
        currentIndex.set(movieLogic.processIndexDown(currentIndex.get(), movies.size()));
        return movieLogic.getMediaAtIndex(currentIndex.get(), movies);
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

    public DoubleProperty volumeLevelProperty() {
        return this.volumeLevel;
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

    public void applyFilter(String filter) throws MoviesException {
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


    /**
     * used to control the playback functionality
     */
    public boolean getObservablePlayPropertyValue() {
        return observablePlayProperty.get();
    }

    /**
     * used to control the playback functionality
     */
    public void setObservablePlayPropertyValue(boolean observablePlayProperty) {
        System.out.println(observablePlayProperty + "from model");
        this.observablePlayProperty.set(observablePlayProperty);
    }


    /**
     * Checks if the category for deletion is open
     */
    public boolean checkOpenCategory(Category categoryToDelete) {
        return this.categoryLogic.checkOpenCategory(this.currentOpenedCategory, categoryToDelete.getId());
    }


    /**
     * total time duration off the movie
     */
    public double getTotalTime() {
        return totalTime.get();
    }

    /**
     * total time duration off the movie
     */
    public DoubleProperty totalTimeProperty() {
        return totalTime;
    }

    /**
     * total time duration off the movie
     */
    public void setTotalTime(double totalTime) {
        this.totalTime.set(totalTime);
    }

    public boolean isMoviePlaying(Movie selectedMovie) {
        return movieLogic.checkIfMovieIsPlaying(selectedMovie.getId(), this.currentMovieSelected.get());
    }


    /**Updates the movie last view date when the play button is pressed
     * @param id movie that is currently played*/
    public void verifyViewDate(int id) throws MoviesException {
        boolean isCurrentDateEqualWithLastView =movieCreation.compareCurrentDateWithMovieLastViewDate(movieObjects.get(id).getLastView());
        if(isCurrentDateEqualWithLastView){
            return ;
        }
        movieCreation.saveCurrentViewDate(id);
    }



}



