package gui.components.newEditDeleteCategory;

import exceptions.MoviesException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utility.ExceptionHandler;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class NewEditController implements Initializable {
    private CategoryReloadable reloadable;
    private CategoryModel categoryModel;



    /**
     * Initialize the model , if an error has occurred will not show the window
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setCategoryModel(CategoryModel.getInstance());
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage());
            return;
        }
        setOnChangeListener(getCategoryTitle(), getInformation());
    }

    public CategoryReloadable getRealoadable(){
        return reloadable;
    }


    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel){
        this.categoryModel = categoryModel;
    }


}
