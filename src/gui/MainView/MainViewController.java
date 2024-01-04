package gui.MainView;

import be.Category;
import exceptions.MoviesException;
import gui.components.category.CategorySelectionHandler;
import gui.components.category.CategoryView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    private MainModel model;
    @FXML
    private VBox categoryContainer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            model=MainModel.getInstance();
            System.out.println( categoryContainer.getChildren().size());
            categoryContainer.getChildren().add(new CategoryView(new CategorySelectionHandler(),model.getCategories()));
            System.out.println( categoryContainer.getChildren().size());
        }catch (MoviesException me){
          Alert alert =new Alert(Alert.AlertType.ERROR,me.getMessage());
          alert.show();
        }
    }
}
