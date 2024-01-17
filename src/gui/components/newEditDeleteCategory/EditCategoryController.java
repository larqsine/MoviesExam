package gui.components.newEditDeleteCategory;

import be.Category;
import exceptions.MoviesException;
import javafx.event.ActionEvent;
import utility.ExceptionHandler;

import static utility.Utility.getCurrentStage;

public class    EditCategoryController extends NewEditController{



    public void cancelCategory(ActionEvent event) {
        getCategoryModel().cancelUpdateCategory();
        getCurrentStage(event).close();
    }

    public void saveCategory(ActionEvent event) {
        if (!isValidTitle()){
            showTitleError();
            return;
        }
        if (updateCategory()){
            getReloadable().reloadCategoriesFromDB();
        }
        closeCurrentStage(event);
    }

    private boolean isValidTitle(){
        String title = getCategoryTitle().getText();
        return !getCategoryModel().checkTitle(title);
    }

    private boolean updateCategory() {
        try {
            String title = getCategoryTitle().getText();
            return getCategoryModel().updateCategory(title);
        } catch (MoviesException e) {
                ExceptionHandler.displayErrorAlert(e.getMessage(),null);
                return false;
        }
    }

    private void closeCurrentStage(ActionEvent event){
        getCurrentStage(event).close();
    }







}
