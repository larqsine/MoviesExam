package gui.components.newEditDeleteCategory;

import be.Category;
import be.Movie;
import bll.CategoryLogic;
import exceptions.MoviesException;

public class CategoryModel {

    private CategoryLogic categoryLogic;
    private static CategoryModel instance;

    /**
     * Stores the current selected category used for edit or delete operations
     */
    private Category currentSelectedCategory;
    /**
     * stores the current selected movie to be added to the playlist
     */
    private Movie currentSelectedMovie;

    private CategoryModel() throws MoviesException{
        categoryLogic = CategoryLogic.getInstance();
    }

    public boolean createNewCategory(String categoryTitle) throws MoviesException {
        Category categoryCreated = new Category(categoryTitle);
        return categoryLogic.createCategory(categoryCreated);
    }

    public static CategoryModel getInstance() throws MoviesException {
        if (instance == null){
            instance = new CategoryModel();
        }
        return instance;
    }
}
