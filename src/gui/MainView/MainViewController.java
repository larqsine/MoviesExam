package gui.MainView;

import be.Category;
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
import gui.components.newEditDeleteMovies.AddMovieController;
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
import gui.filterSongs.FilterManager;
import utility.*;


import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
    private ISearchGraphic searchGraphic;
    @FXML
    private Label infoEmptyLabel;
    @FXML
    private Button playButton;
    @FXML
    private MediaView mediaViewPlayer;
    private ISearchGraphic isearchGraphic;

    @FXML
    private Pane categoryContainer;
    private CategoryView categoryView;
    @FXML
    private Pane moviesView;
    private CategoryReloadable categoryReloadable;
    private PlayerControl playerControl;
    private PlayerCommander playerCommander;

    @FXML
    private void applyFilter(ActionEvent event) {
        FilterManager filterManager = new FilterManager(this.model, searchGraphic, infoEmptyLabel, searchButton, searchValue);
        filterManager.applyFilter(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIInitializer uiInitializer = new UIInitializer();
        try {
            model = MainModel.getInstance();
            //initialize application playback
            playButton.textProperty().bind(model.playButtonValueProperty());
            PlayOperations playOperations = PlayOperationsHandler.getInstance(model);
            DataSupplier dataHandler = DataHandler.getInstance(model,playOperations);
            MediaViewReloader mediaViewReloader = new MediaViewUpdate(mediaViewPlayer);
            playerControl = Player.useMediaPlayer(dataHandler,mediaViewReloader);
            playerCommander= new PlayerCommander(dataHandler,playerControl);
            mediaViewPlayer.setMediaPlayer(playerControl.getMediaPlayer());
            //initialize the category list view
            categoryView = new CategoryView(new CategorySelectionHandler(model), model.getCategories());
            categoryContainer.getChildren().add(categoryView);
            //initialize moviesTable data
            moviesView.getChildren().add(new MoviesTable(new MovieSelectionHandler(this.model,playerCommander,playButton),model.getMovies(),model,playerCommander));
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
@FXML
    private void addNewCategory(ActionEvent event) {
        CategoryReloadable categoryReloadable = CategoryReloadableHandler.getInstance(this.model);
        try{
            Stage mainStage = Utility.getCurrentStage(event);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/newEditDeleteCategory/NewCategoryView.fxml"));
            Parent parent =loader.load();
            NewCategoryControllerRel categoryControllerRel  = loader.getController();
            categoryControllerRel.setReloadable(categoryReloadable);
            Scene scene = new Scene(parent);
            Stage newSongStage = Utility.createPopupStage(mainStage, scene, Titles.ADD_CATEGORY.getValue(), POPUP_WIDTH);
            newSongStage.show();
        } catch (IOException e) {
            ExceptionHandler.displayErrorAlert(InformationalMessages.FXML_MISSING,"Application error");
        }   
    }

    public void editCategory(ActionEvent actionEvent) {
        Category categoryToUpdate = getSelectedCategory();
        if (categoryToUpdate == null){
            ExceptionHandler.displayErrorAlert(InformationalMessages.NO_CATEGORY_SELECTED, "No category has been selected");
            return;
        }
        try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/newEditDeleteCategory/NewCategoryView.fxml"));
            Parent root = loader.load();
            NewCategoryControllerRel categoryControllerRel  = loader.getController();
            categoryControllerRel.setReloadable(categoryReloadable);
            Scene scene = new Scene(root);
            Stage mainStage = Utility.getCurrentStage(actionEvent);
            Stage newCategoryStage = Utility.createPopupStage(mainStage, scene, Titles.EDIT_CATEGORY.getValue(),POPUP_WIDTH);
            newCategoryStage.show();
        } catch (IOException e){
            ExceptionHandler.displayErrorAlert(InformationalMessages.FXML_MISSING, "Application error");
        }
    }

    private Category getSelectedCategory() {
        return this.categoryView.getSelectionModel().getSelectedItem();
    }

    public void addNewMovie(ActionEvent event) {
        String resourcePath=  "../components/newEditDeleteMovies/AddViewMovie.fxml";
        if(model.getCurrentOpenedCategory()==0){
            ExceptionHandler.displayInformationAlert(InformationalMessages.NO_CATEGORY_OPENED,"Please open a category");
       return;
    }
        try {
        FXMLLoader loader =new FXMLLoader(getClass().getResource(resourcePath));
        Parent root = loader.load();
        AddMovieController adMovie = loader.getController();
        adMovie.getCurrentOpenedCategory(model.getCurrentOpenedCategory());
        adMovie.setModel(this.model);
        Scene scene = new Scene(root);
        Stage mainStage = Utility.getCurrentStage(event);
        Stage newCategoryStage = Utility.createPopupStage(mainStage, scene, Titles.ADD_NEW_MOVIE.getValue(),POPUP_WIDTH);
        newCategoryStage.show();
    } catch (IOException e) {
        ExceptionHandler.displayErrorAlert(InformationalMessages.FXML_MISSING, "Application error");
    }
    }

    private Parent getParent(String resourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
        return loader.load();
    }

    public void playPreviousMovie(ActionEvent event) {
    }

    public void playMovie(ActionEvent event) {

        if(model.getPlayButtonState()){
            this.playerCommander.processOperation(Operations.PAUSE);
            model.setPlayButtonValue(PlayButtonGraphic.PLAY.getValue());
            model.setPlayButtonState(false);
        }
        else{
            this.playerCommander.processOperation(Operations.PLAY);
            model.setPlayButtonValue(PlayButtonGraphic.STOP.getValue());
            model.setPlayButtonState(true);
        }
//      if(this.playButton.getText().trim().equals(PlayButtonGraphic.STOP.getValue())){
//
//      }else{
//
//      }

    }

    public void playNextMovie(ActionEvent event) {
    }


}
