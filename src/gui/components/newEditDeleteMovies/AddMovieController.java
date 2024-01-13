package gui.components.newEditDeleteMovies;
import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.components.newEditDeleteMovies.genreView.GenreView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utility.ExceptionHandler;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMovieController extends NewEditController implements Initializable {
    @FXML
    private TextField movieTitle;
    @FXML
    private Button createButton;
    private MovieModel model;
    private MainModel mainModel;
    @FXML
    private HBox genresContainer;
    @FXML
    private GenreView genreList;
    @FXML
    private Stage currentStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            model = MovieModel.getInstance();
            mainModel = MainModel.getInstance();
            genreList = new GenreView();
            genreList.setGenres(model.getGenres());
            genresContainer.getChildren().add(genreList);
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getExceptionsMessages(),"Add movie error");
        }


    }



private boolean checkIfCategorySelected(SimpleIntegerProperty catId){
   return   catId.getValue()!=0;
}

private void disableButton(Button button,boolean isCategoryOpened){
   button.setDisable(isCategoryOpened);
}

private void displayInfoMessage(boolean isCategorySelected){
        if(!isCategorySelected){
//            initiateInfoAlert();
        }
}


@Override
    public void cancelAddEditMovie(ActionEvent event) {
        System.out.println("sads");
    }


@Override
    public void saveAddEditMovie(ActionEvent event) {
        System.out.println("asd");
    }


@Override
    public void openFileChooser(ActionEvent event) {
        System.out.println("asd");
    }

public void getStage(Stage stage){
this.currentStage=stage;
}
}
