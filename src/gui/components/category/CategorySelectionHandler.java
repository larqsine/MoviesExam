package gui.components.category;
import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.components.listeners.CategorySelection;
import gui.components.listeners.DataSupplier;
import utility.ExceptionHandler;

public class CategorySelectionHandler  implements CategorySelection {

    private MainModel model ;
    public CategorySelectionHandler(MainModel model) {
        this.model=model;
    }

    @Override
    public void categorySelectionHandler(int id) {
//        try {
//            model.retrieveMovies(id);
//        } catch (MoviesException e) {
//            ExceptionHandler eh = new ExceptionHandler();
//            eh.displayErrorAlert(e.getMessage(),"Movies error");
//        }
    }
}
