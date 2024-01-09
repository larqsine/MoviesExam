package gui.components.newEditDeleteCategory;

import be.Category;
import exceptions.MoviesException;
import javafx.event.ActionEvent;
import utility.ExceptionHandler;

public class EditCategoryController extends NewEditController{
    @Override
    public void cancelCategory(ActionEvent event) {
        getCategoryModel().cancelUpdateCategory();
        getCurrentStage(event).close();
    }

    @Override
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
        } catch (MatchException e){
            ExceptionHandler.displayErrorAlert(e.getMessage());
            return false;
        } catch (MoviesException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeCurrentStage(ActionEvent event){
        getCurrentStage(event).close();
    }

    public void setCategoryToEdit(Category category){
        getCategoryModel().setCurrentSelectedCategory(category);
        getCategoryTitle().setText(category.getName());
    }

}
