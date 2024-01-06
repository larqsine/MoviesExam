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

import java.util.Collections;
import java.util.Map;

public class MainModel {
    private final CategoryLogicAPI categoryLogic;
    private final MovieLogicAPI movieLogic;
    private static MainModel instance;
    private final ObservableList<Category> categories;
    private final ObservableList<Movie>movies;
    private Map<Integer,Movie> movieObjects ;



    private MainModel() throws MoviesException  {
        this.movieLogic=new MovieLogic();
        this.categoryLogic=new CategoryLogic();
        this.categories = FXCollections.observableArrayList();
        this.movies= FXCollections.observableArrayList();
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

    public void retrieveMovies(int id) throws MoviesException {
        setMovieObservableList(id);
    }
    private void setMovieObservableList(int categoryId) throws MoviesException {
         movieObjects= movieLogic.getMovies(categoryId);
         this.movies.setAll(movieObjects.keySet().stream().map(elem->movieObjects.get(elem)).toList());
    }

//    public void applyFilter(String filter) {
//        this.movies.setAll(movieLogic.applyFilter);
//    }
//
//    public void resetFilter() {
//    }
}
