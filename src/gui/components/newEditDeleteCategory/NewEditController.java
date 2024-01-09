package gui.components.newEditDeleteCategory;

import exceptions.MoviesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.ExceptionHandler;
import utility.InformationalMessages;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class NewEditController implements Initializable {
    private CategoryReloadable reloadable;



    private CategoryModel categoryModel;
    @FXML
    private TextField categoryTitle;
    @FXML
    private Label information;
    @FXML
    private Button saveUpdateButton;


    /**
     * Initialize the model , if an error has occurred will not show the window
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setCategoryModel(CategoryModel.getInstance());
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage(), null);
            return;
        }
        setOnChangeListener(getCategoryTitle(), getInformation());
    }


    /**
     * Abstract method to cancel category creation/editing.
     *
     * @param event The ActionEvent that triggered the cancellation.
     */
    public abstract void cancelCategory(ActionEvent event);

    /**
     * Abstract method to save/update a category.
     *
     * @param event The ActionEvent that triggered the save/update.
     */
    public abstract void saveCategory(ActionEvent event);

    public CategoryReloadable getReloadable() {
        return reloadable;
    }


    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public TextField getCategoryTitle() {
        return categoryTitle;
    }

    public Label getInformation() {
        return information;
    }

    public Button getSaveUpdateButton() {
        return saveUpdateButton;
    }

    public void setOnChangeListener(TextField textField, Label label) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (label.isVisible()) {
                label.setVisible(false);
            }
        });
    }

    public Stage getCurrentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public void showTitleError() {
        getInformation().setText(InformationalMessages.NO_EMPTY_TITLE.getValue());
        getInformation().setVisible(true);
    }

    public void setReloadable(CategoryReloadable reloadable) {
        this.reloadable = reloadable;
    }


}
