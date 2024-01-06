package gui.MainView;

import exceptions.MoviesException;
import gui.components.category.CategorySelectionHandler;
import gui.components.category.CategoryView;
import gui.components.movies.MovieSelectionHandler;
import gui.components.movies.MoviesTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    private MainModel model;
    private  boolean error = false;
    private String exceptionMessage;



    @FXML
    private Pane categoryContainer;
    @FXML
    private Pane moviesView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            model = MainModel.getInstance();
            //initialize the category list view
            categoryContainer.getChildren().add(new CategoryView(new CategorySelectionHandler(), model.getCategories()));
        //initialize moviesTable
            moviesView.getChildren().add(new MoviesTable(new MovieSelectionHandler(),model.getMovies()));
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
