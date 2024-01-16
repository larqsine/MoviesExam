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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.MovieFormat;

import java.util.List;
import java.util.Map;

public class MovieModel {
    private Map<String, Genre> genresObjects;
    private final ObservableList<String> genres;
    private MovieLogicAPI movieLogic;
    private GenreLogicApi genreLogic;
    private MovieCreation movieCreation;

    public ObservableList<String> getGenres() {
        return genres;
    }

    private static MovieModel instance;

    public static MovieModel getInstance() throws MoviesException {
        if (instance == null) {
            instance = new MovieModel();
        }
        return instance;
    }

    private MovieModel() throws MoviesException {
     this.movieLogic= new MovieLogic();
     this.movieCreation= new MovieCreation();
     this.genreLogic =new GenreLogic();
     this.genres = FXCollections.observableArrayList();
        initializeGenresObjects();
        initializeGenres();
    }

    private void initializeGenres() {
        this.genres.setAll(genreLogic.getGenreValues(this.genresObjects));
    }
    private void initializeGenresObjects() throws MoviesException {
        this.genresObjects=genreLogic.getGenres();
    }


    public boolean deleteMovie(Movie movieToDelete) {
        return false;
    }

    public void cancelUpdateMovie() {
    }

    public void setCurrentSelectedMovie(Movie movie) {
    }

    public boolean updateMovie(String title) throws Exception{
        return false;
    }

    public boolean checkTitle(String title) {
        return false;
    }


    /**
     * get the format off the file , it is used by getDuration() method
     * @param name  it is the name off the file that is being processed */
    public MovieFormat getFormat(String name) throws MoviesException {
        return movieCreation.extractFormat(name);
    }

    public boolean areTitleOrPathEmpty(String title, String path) {
        return movieCreation.areTitleOrPathEmpty(title,path);
    }

    public boolean checkIfFileExists(String path) {
        return movieCreation.checkFilePath(path);
    }

    public boolean saveMovie(String title, String path, List<String> genres, int categoryId) throws MoviesException {
     List<Genre> genreObjects  = genreLogic.convertStringsToGenre(this.genresObjects,genres);
     Movie movie =  new Movie(title,null,path,null,null,genreObjects);
     return movieCreation.saveMovie(movie,categoryId);
    }

    /*
      Used  to insert movie genres into the database
      */
//    public void insertGenres(){
//        List<String> genres = Arrays.stream(MovieGenre.values()).map(MovieGenre::getDisplayName).toList();
//        this.movieLogic.insertGenres(genres);
//    }
}
