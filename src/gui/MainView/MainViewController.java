package gui.MainView;

import be.Category;
import exceptions.MoviesException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    private MainModel model;
    @FXML
    private ListView<Category> categoryView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            model=MainModel.getInstance();
            categoryView.setItems(model.getCategories());
            categoryView.getItems().forEach(elem->System.out.println(elem.getId()));

        }catch (MoviesException me){
          Alert alert =new Alert(Alert.AlertType.ERROR,me.getMessage());
          alert.show();
        }
    }
}
