package gui.MainView;
import be.Category;
import be.Movie;
import bll.CategoryLogic;
import bll.CategoryLogicAPI;
import bll.movieLogic.MovieLogic;
import bll.movieLogic.MovieLogicAPI;
import exceptions.MoviesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class MainModel {
    private final CategoryLogicAPI categoryLogic;
    private final MovieLogicAPI movieLogic;
    private static MainModel instance;
    private final ObservableList<Category> categories;
    private final ObservableList<Movie>movies;
    private Map<Integer,Movie> categoryMovies ;



    private MainModel() throws MoviesException  {
        this.categoryLogic=new CategoryLogic();
        this.movieLogic= new MovieLogic();
        this.categories = FXCollections.observableArrayList();
        this.movies= FXCollections.observableArrayList();
        this.categoryMovies= new HashMap<>();
        initializeCategories();
    }

    public static MainModel getInstance() throws MoviesException {
        if(instance==null){
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

    public void retrieveMovies(int categoryId) throws MoviesException {
     this.categoryMovies= movieLogic.getMovies(categoryId);
     populateMoviesObservable();
    }
    private void populateMoviesObservable(){
      this.movies.setAll(this.categoryMovies.keySet().stream().map(elem->categoryMovies.get(elem)).toList());
    }

}
