package bll;

import be.Category;
import be.Movie;
import exceptions.MoviesException;
import javafx.collections.ObservableList;

import java.util.List;

public interface CategoryLogicAPI {
    List<Category> getAllCategories() throws MoviesException;

    Movie applyFilter(String filter, ObservableList<Movie> movies);
}

  
