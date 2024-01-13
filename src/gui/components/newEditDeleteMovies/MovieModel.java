package gui.components.newEditDeleteMovies;

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

public class MovieModel {

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
        initializeGenres();
    }

    private void initializeGenres() throws MoviesException {
        System.out.println(genreLogic.getGenres().size());
        this.genres.setAll(genreLogic.getGenres());
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

    /*
      Used  to insert movie genres into the database
      */
//    public void insertGenres(){
//        List<String> genres = Arrays.stream(MovieGenre.values()).map(MovieGenre::getDisplayName).toList();
//        this.movieLogic.insertGenres(genres);
//    }
}
