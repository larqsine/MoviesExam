package gui.MainView;

import be.Category;
import exceptions.MoviesException;
import gui.components.category.CategorySelectionHandler;
import gui.components.category.CategoryView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableRow;
import javafx.scene.layout.VBox;
import utility.ExceptionHandler;
import utility.ExceptionsMessages;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.ErrorManager;

public class MainViewController implements Initializable {

    private MainModel model;
    private  boolean error = false;
    private String exceptionMessage;



    @FXML
    private VBox categoryContainer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            model = MainModel.getInstance();
            categoryContainer.getChildren().add(1,new CategoryView(new CategorySelectionHandler(), model.getCategories()));
        } catch (MoviesException me) {
            this.error=true;
            this.exceptionMessage=me.getMessage();
        }
    }

    public boolean isError() {
        return error;
    }
    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
