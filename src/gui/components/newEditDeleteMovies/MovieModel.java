package gui.components.newEditDeleteMovies;

import be.Category;
import be.Genre;
import be.Movie;
import bll.genreLogic.GenreLogic;
import bll.genreLogic.GenreLogicApi;
import bll.movieLogic.MovieCreation;
import bll.movieLogic.MovieLogic;
import bll.movieLogic.MovieLogicAPI;
import exceptions.MoviesException;
import gui.components.newEditDeleteCategory.CategoryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.MovieFormat;

import java.util.List;
import java.util.Map;

public class MovieModel {
    private Map<String, Genre> genresObjects;
    private Map<String, Category> categoriesObjects;
    private final ObservableList<String> genres;
    private final ObservableList<String> categories;
    private MovieLogicAPI movieLogic;
    private GenreLogicApi genreLogic;
    private MovieCreation movieCreation;


    private Movie currentSelectedMovie;


    private static MovieModel instance;

    public static MovieModel getInstance() throws MoviesException {
        if (instance == null) {
            instance = new MovieModel();
        }
        return instance;
    }

    private MovieModel() throws MoviesException {
        this.movieLogic = new MovieLogic();
        this.movieCreation = new MovieCreation();
        this.genreLogic = new GenreLogic();
        this.categories = FXCollections.observableArrayList();
        this.genres = FXCollections.observableArrayList();
        initializeGenresObjects();
        initializeGenres();
        initializeCategoryObjects();
        initializeCategories();
    }

    private void initializeGenres() {
        this.genres.setAll(genreLogic.getGenreValues(this.genresObjects));
    }

    private void initializeGenresObjects() throws MoviesException {
        this.genresObjects = genreLogic.getGenres();
    }

    private void initializeCategories() {
        this.categories.setAll(genreLogic.getCategoriesValues(this.categoriesObjects));
    }

    private void initializeCategoryObjects() throws MoviesException {
        this.categoriesObjects = genreLogic.getCategories();
    }

    public void cancelUpdateMovie() {
    }

    public void setCurrentSelectedMovie(Movie currentSelectedMovie) {
        this.currentSelectedMovie = currentSelectedMovie;
    }
    /** retrieve the current selected movie for editing*/
    public Movie getCurrentSelectedMovie() {
        return currentSelectedMovie;
    }

    public boolean updateMovie(String title) throws Exception {
        return false;
    }

    public boolean checkTitle(String title) {
        return false;
    }

    public ObservableList<String> getGenres() {
        return genres;
    }


    /**
     * get the format off the file , it is used by getDuration() method
     *
     * @param name it is the name off the file that is being processed
     */
    public MovieFormat getFormat(String name) throws MoviesException {
        System.out.println("I am here");
        return movieCreation.extractFormat(name);
    }

    public boolean areTitleOrPathEmpty(String title, String path) {
        return movieCreation.areTitleOrPathEmpty(title, path);
    }

    public boolean checkIfFileExists(String path) {
        return movieCreation.checkFilePath(path);
    }

    public boolean saveMovie(String title, String path, List<String> genres,List<String>categories, int categoryId) throws MoviesException {
        List<Genre> genreObjects = genreLogic.convertStringsToGenre(this.genresObjects, genres);
        List<Category> catObjects = genreLogic.convertStringsToCategory(this.categoriesObjects,categories);
        Movie movie = new Movie(title, null, path, null, null, genreObjects);

        return movieCreation.saveMovie(movie, catObjects);
    }

    /**
     * Delete a movie from the database
     *
     * @param movieToDelete The movie to be deleted
     * @return True if the movie was deleted successfully, false otherwise
     */
    public boolean deleteMovie(Movie movieToDelete) throws MoviesException {
        return movieCreation.deleteMovie(movieToDelete);
    }


    public ObservableList<String> getCategories() {
        return this.categories;
    }


    /*
      Used  to insert movie genres into the database
      */
//    public void insertGenres(){
//        List<String> genres = Arrays.stream(MovieGenre.values()).map(MovieGenre::getDisplayName).toList();
//        this.movieLogic.insertGenres(genres);
//    }
}
