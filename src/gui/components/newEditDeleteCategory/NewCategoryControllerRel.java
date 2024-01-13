package gui.components.newEditDeleteCategory;
import exceptions.MoviesException;
import javafx.event.ActionEvent;
import utility.ExceptionHandler;


public class NewCategoryControllerRel extends NewEditController {

    /**
     * Create a new category
     */
    public void saveCategory(ActionEvent event) {
        String title = getCategoryTitle().getText();
        if (isTitleEmpty(title)) {
            showTitleError();
            return;
        }
        if (createCategory(title)) {
            getReloadable().reloadCategoriesFromDB();
        }
        getCurrentStage(event).close();
    }

    private boolean isTitleEmpty(String title) {
        return getCategoryModel().checkTitle(title);
    }

    private boolean createCategory(String title) {
        try {
            getCategoryModel().createNewCategory(title);
            return true;
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage(),null);
            return false;
        }
    }

    @Override
    public void cancelUpdateMovie(ActionEvent event) {

    }

    /**
     * Cancel new category creation
     */
    @Override
    public void cancelCategory(ActionEvent event) {
        getCurrentStage(event).close();
    }
}
