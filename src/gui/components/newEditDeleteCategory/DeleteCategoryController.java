package gui.components.newEditDeleteCategory;

import be.Category;
import exceptions.MoviesException;
import gui.components.confirmationWindow.ConfirmationWindow;
import gui.components.listeners.ConfirmationController;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import utility.ExceptionHandler;
import utility.InformationalMessages;
import utility.Titles;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteCategoryController implements ConfirmationController, Initializable {
    private CategoryModel categoryModel;
    private VBox confirmationWindow;
    private CategoryReloadable categoryReloadable;
    private Category categoryToDelete;

    @Override
    public void confirmationEventHandler(boolean confirmation) {

    }

    @Override
    public void confirmationEventHandeler(boolean confirmation) {
        boolean deleted;
        if (confirmation){
            try {
                deleted = categoryModel.deleteCategory(this.categoryToDelete);
                if (deleted){
                    Platform.runLater(() ->{
                        ExceptionHandler.displayErrorAlert(InformationalMessages.DELETE_SUCCEEDED, "Delete Category");
                    });
                    categoryReloadable.reloadCategoriesFromDB();
                }
            } catch (MoviesException e){
                ExceptionHandler.displayErrorAlert(e.getMessage(),null);
            }
        }
    }

  /**  @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            categoryModel = CategoryModel.getInstance();
        } catch (MoviesException e){
            ExceptionHandler.displayErrorAlert(e.getMessage());
        }
        if (categoryModel != null) {
            ConfirmationWindow confirmationView = new ConfirmationWindow();
            if (confirmationView.getConfirmationWindow()==null){
                return;
            }
            confirmationWindow = confirmationView.getConfirmationWindow();
            initializeConfirmationWindow(confirmationView, this);
        }
    }*/

    private void initializeConfirmationWindow(ConfirmationWindow confirmationWindow, ConfirmationController confirmationController) {
        confirmationWindow.setConfirmationController(confirmationController);
        confirmationWindow.setOperationTitle(Titles.DELETE_CATEGORY.getValue());
        String category = "\"" + this.categoryToDelete.getName() + "\" ?";
        confirmationWindow.setOperationTitle(InformationalMessages.DELETE_CATEGORY_QUESTION.getValue() + category);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
