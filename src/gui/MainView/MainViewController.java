package gui.MainView;

import exceptions.MoviesException;
import gui.components.category.CategorySelectionHandler;
import gui.components.category.CategoryView;
import gui.components.listeners.DataSupplier;
import gui.components.listeners.MediaViewReloader;
import gui.components.movies.MovieSelectionHandler;
import gui.components.movies.MoviesTable;
import gui.components.newEditDeleteCategory.CategoryReloadable;
import gui.components.newEditDeleteCategory.CategoryReloadableHandler;
import gui.components.newEditDeleteCategory.NewCategoryControllerRel;
import gui.components.player.*;
import gui.playOperations.PlayOperations;
import gui.playOperations.PlayOperationsHandler;
import gui.searchButton.ISearchGraphic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import utility.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    private final int POPUP_WIDTH = 420;
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
    @FXML
    private Pane mediaContainer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIInitializer uiInitializer = new UIInitializer();


        try {

            model = MainModel.getInstance();
            PlayOperations playOperations = PlayOperationsHandler.getInstance(model);
            DataSupplier dataHandler = DataHandler.getInstance(model,playOperations);
            MediaViewReloader mediaViewReloader = new MediaViewUpdate(mediaViewPlayer);
            PlayerControl playerControl = Player.useMediaPlayer(dataHandler,mediaViewReloader);
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

    public void addNewCategory(ActionEvent event) {
        CategoryReloadable categoryReloadable = CategoryReloadableHandler.getInstance(this.model);
        try{
            Stage mainStage = Utility.getCurrentStage(event);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/newEditDeleteCategory/NewCategoryView.fxml"));
            Parent parent =loader.load();
            NewCategoryControllerRel categoryControllerRel  = loader.getController();
            categoryControllerRel.setReloadable(categoryReloadable);
            Scene scene = new Scene(parent);
            Stage newSongStage = Utility.createPopupStage(mainStage, scene, Titles.EDIT_SONG.getValue(), POPUP_WIDTH);
            newSongStage.show();
        } catch (IOException e) {
            ExceptionHandler.displayErrorAlert(InformationalMessages.FXML_MISSING,"Application error");
        }


    }
}
