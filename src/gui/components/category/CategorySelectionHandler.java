package gui.components.category;

import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.components.listeners.CategorySelection;
import javafx.beans.property.SimpleIntegerProperty;
import utility.ExceptionHandler;

public class CategorySelectionHandler implements CategorySelection {

    private MainModel model;

    public CategorySelectionHandler(MainModel model) {
        this.model = model;
    }

    @Override
    public void categorySelectionHandler(int id) {
        try {
            model.retrieveMovies(id);
            model.currentOpenedCategoryProperty().bind(new SimpleIntegerProperty(id));
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage(), "Movies error");
        }
    }
}
