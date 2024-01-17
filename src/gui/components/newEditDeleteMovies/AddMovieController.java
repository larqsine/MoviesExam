package gui.components.newEditDeleteMovies;

import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.components.category.CategoryView;
import gui.components.listeners.MovieReloadable;
import gui.components.newEditDeleteMovies.genreView.MultipleChoiceView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utility.*;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddMovieController extends NewEditController implements Initializable {
    public HBox categoryContainer;
    @FXML
    private TextField movieTitle;
    @FXML
    private Button createButton;
    @FXML
    private TextField fileLocation;
    private MovieModel model;
    @FXML
    private HBox genresContainer;

    @FXML
    private MultipleChoiceView genreList;
    @FXML
    private MultipleChoiceView  categorySelectionView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            model = MovieModel.getInstance();
            genreList = new MultipleChoiceView();
            genreList.setElements(model.getGenres());
            genresContainer.getChildren().add(genreList);
            categorySelectionView=new MultipleChoiceView();
            categorySelectionView.setElements(model.getCategories());
            categoryContainer.getChildren().add(categorySelectionView);
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getExceptionsMessages(), "Add movie error");
        }
    }

    @Override
    public void cancelAddEditMovie(ActionEvent event) {
        closeStage(Utility.getCurrentStage(event));
    }

    @Override
    public void saveAddEditMovie(ActionEvent event) {
        String title = movieTitle.getText();
        String path = fileLocation.getText();
        List<String> categories = this.categorySelectionView.getSelectedElements();
        List<String> genres = this.genreList.getSelectedElements();
        if (validateInputs(title, path,this.model)) {
            return;
        }
        if(categories.isEmpty()){
            ExceptionHandler.displayInformationAlert(InformationalMessages.NO_CATEGORY_CHECKED.getValue(),"Data incomplete");
            return;
        }

        try {
            boolean isSuccess = model.saveMovie(title, path, genres, categories,this.getOpenedCategory());
            if (!isSuccess) {
                ExceptionHandler.displayErrorAlert(ExceptionsMessages.DB_UNSUCCESFULL.getValue(), "Unsuccessful operation");
            }
            this.getReloadableController().reloadMovies();
            closeStage(Utility.getCurrentStage(event));
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(ExceptionsMessages.DB_UNSUCCESFULL.getValue(), "Unsuccessful operation");
            closeStage(Utility.getCurrentStage(event));
        }
    }
    @Override
    public void openFileChooser(ActionEvent event) {
        Stage newMovieStage = Utility.getCurrentStage(event);
        File selectedFile = getFileChooser().showOpenDialog(newMovieStage);
        if (selectedFile != null) {
            try {
                MovieFormat movieformat = setMovieFormat(selectedFile);
                this.fileLocation.setText(selectedFile.getPath());
            } catch (MoviesException e) {
                ExceptionHandler.displayErrorAndWait(e.getMessage(), "Format unsupported");
            }
        }
    }




    public void getCurrentOpenedCategory(int currentOpenedCategory) {
        this.setOpenedCategory(currentOpenedCategory);
    }

    public void setModel(MainModel model){
        MovieReloadable movieReloadable = new MovieReloader(model);
        this.setReloadableController(movieReloadable);
    }


    private MovieFormat setMovieFormat(File file) throws MoviesException {
        return model.getFormat(file.getName());
    }
}
