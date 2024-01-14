package gui.components.newEditDeleteCategory;

import be.Category;
import be.Movie;
import bll.CategoryLogic;
import bll.CategoryLogicAPI;
import exceptions.MoviesException;

public class CategoryModel {

    private CategoryLogicAPI categoryLogic;
    private static CategoryModel instance;

    /**
     * Stores the current selected category used for edit or delete operations
     */
    private Category currentSelectedCategory;
    /**
     * stores the current selected movie to be added to the playlist
     */
    private Movie currentSelectedMovie;

    private CategoryModel() throws MoviesException {
        categoryLogic = CategoryLogic.getInstance();
    }

    public boolean createNewCategory(String categoryTitle) throws MoviesException {
        return categoryLogic.createCategory(categoryTitle);
    }

    public static CategoryModel getInstance() throws MoviesException {
        if (instance == null) {
            instance = new CategoryModel();
        }
        return instance;
    }

    public boolean checkTitle(String title) {
        return categoryLogic.checkTitle(title);
    }

    public boolean updateCategory(String newTitle) throws MoviesException {
      //  this.currentSelectedCategory = null;
        return this.categoryLogic.updateCategory(this.currentSelectedCategory.getId(), newTitle);
    }

    public void cancelUpdateCategory() {
        this.currentSelectedCategory = null;
    }

    public void setCurrentSelectedCategory(Category currentSelectedCategory) {
        this.currentSelectedCategory = currentSelectedCategory;
    }

    public boolean deleteCategory(Category categoryToDelete) throws MoviesException {
        return this.categoryLogic.deleteCategory(categoryToDelete.getId());
    }
}
