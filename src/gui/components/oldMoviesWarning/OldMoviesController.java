package gui.components.oldMoviesWarning;
import exceptions.MoviesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import utility.ExceptionHandler;
import utility.Utility;
import java.net.URL;
import java.util.ResourceBundle;

public class OldMoviesController implements Initializable {

    private OldMoviesModel model;
    @FXML
    private ListView<String> oldMoviesList;
    private Stage windowStage;

    @FXML
    private void closeWindow(ActionEvent event) {
        Utility.getCurrentStage(event).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            model = OldMoviesModel.getInstance();
            oldMoviesList.setItems(model.getOldMovies());
            oldMoviesList.setStyle("-fx-effect: dropshadow(gaussian, #494849, 15, 0, 8, 8); -fx-border-color: #000000");
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage(), "Data error");
            windowStage.close();
        }
    }

    public void setStage(Stage stage){
        this.windowStage=stage;
    }
}
