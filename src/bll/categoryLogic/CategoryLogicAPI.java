package bll.categoryLogic;

import be.Category;
import be.Movie;
import exceptions.MoviesException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.List;

public interface CategoryLogicAPI {
    List<Category> getAllCategories() throws MoviesException;
    Movie applyFilter(String filter, ObservableList<Movie> movies);
    boolean createCategory(String categoryTitle) throws MoviesException;

    boolean checkTitle(String title);
    public boolean updateCategory(int categoryId, String newTitle) throws MoviesException;

    boolean deleteCategory(int id) throws MoviesException;
    boolean checkOpenCategory(SimpleIntegerProperty openCategoryId, int currentCategoryToDelete);
}

  
