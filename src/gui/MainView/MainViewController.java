package gui.MainView;

import exceptions.MoviesException;
import gui.components.category.CategorySelectionHandler;
import gui.components.category.CategoryView;
import gui.components.listeners.DataSupplier;
import gui.components.movies.MovieSelectionHandler;
import gui.components.movies.MoviesTable;
import gui.components.player.DataHandler;
import gui.components.player.Player;
import gui.components.player.PlayerCommander;
import gui.components.player.PlayerControl;
import gui.playOperations.PlayOperations;
import gui.playOperations.PlayOperationsHandler;
import gui.searchButton.ISearchGraphic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import utility.UIInitializer;


import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    private MainModel model;
    private  boolean error = false;
    private String exceptionMessage;
    @FXML
    private TextField searchValue;
    @FXML
    private Button searchButton;

    @FXML
    private Label infoEmptyLabel;
    @FXML
    private Button playButton;
    @FXML
    private MediaView mediaViewPlayer;
    private ISearchGraphic isearchGraphic;

    @FXML
    private Pane categoryContainer;
    @FXML
    private Pane moviesView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIInitializer uiInitializer = new UIInitializer();

        try {

            model = MainModel.getInstance();
            PlayOperations playOperations = PlayOperationsHandler.getInstance(model);
            DataSupplier dataHandler = DataHandler.getInstance(model,playOperations);
            PlayerControl playerControl = Player.useMediaPlayer(dataHandler);
            PlayerCommander playerCommander= new PlayerCommander(dataHandler,playerControl);
            mediaViewPlayer.setMediaPlayer(playerControl.getMediaPlayer());
            //initialize the category list view
            categoryContainer.getChildren().add(new CategoryView(new CategorySelectionHandler(model), model.getCategories()));
            //initialize moviesTable data
            moviesView.getChildren().add(new MoviesTable(new MovieSelectionHandler(this.model,playerCommander,playButton),model.getMovies()));
            //initializes the filter view
            uiInitializer.initializeSearchView(isearchGraphic, searchButton, searchValue, infoEmptyLabel);
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
