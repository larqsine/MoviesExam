package gui.MainView;

import be.Category;
import be.Movie;
import exceptions.MoviesException;
import gui.components.PlaybackButton;
import gui.components.category.CategorySelectionHandler;
import gui.components.category.CategoryView;
import gui.components.listeners.DataSupplier;
import gui.components.listeners.MediaViewReloader;
import gui.components.movies.MoviesTable;
import gui.components.newEditDeleteCategory.*;
import gui.components.newEditDeleteMovies.AddMovieController;
import gui.components.newEditDeleteMovies.EditMovieController;
import gui.components.newEditDeleteMovies.MovieReloader;
import gui.components.player.*;
import gui.components.newEditDeleteMovies.DeleteMovieController;
import gui.playOperations.PlayOperations;
import gui.playOperations.PlayOperationsHandler;
import gui.searchButton.ISearchGraphic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import gui.filterSongs.FilterManager;
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
    private ISearchGraphic searchGraphic;
    @FXML
    private Label infoEmptyLabel;
    @FXML
    private Button customPlayButton;
    @FXML
    private HBox  playbackContainer;
    @FXML
    private MediaView mediaView;
    private ISearchGraphic isearchGraphic;
    @FXML
    private Pane categoryContainer;
    private CategoryView categoryView;
    @FXML
    private Pane moviesView;
    @FXML
    private TableView<Movie> moviesTable;
    private PlayerControl playerControl;
    private PlayerCommander playerCommander;
    @FXML
    private Label timeElapsed;
    @FXML
    private Label totalTime;
    @FXML
    private Slider timeSlider;
    @FXML
    private Slider volumeSlider;
    private CategoryReloadable categoryReloadable;


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
            PlayOperations playOperations = PlayOperationsHandler.getInstance(model);
            DataSupplier dataHandler = DataHandler.getInstance(model,playOperations);
            MediaViewReloader mediaViewReloader = new MediaViewUpdate(mediaView,timeSlider);
            playerControl = Player.useMediaPlayer(dataHandler,mediaViewReloader,timeSlider);
            uiInitializer.initializeTimeSlider(timeSlider,model);
            playerCommander= new PlayerCommander(dataHandler,playerControl);
            // mediaViewPlayer.setMediaPlayer(playerControl.getMediaPlayer());
            customPlayButton= new PlaybackButton(92,52,playerCommander,model);
            playbackContainer.getChildren().add(1,customPlayButton);
            //initialize the category list view
            categoryView = new CategoryView(new CategorySelectionHandler(model), model.getCategories());
            categoryContainer.getChildren().add(categoryView);
            //initialize moviesTable data
            moviesTable = new MoviesTable(model.getMovies(),model,playerCommander);
            moviesView.getChildren().add(moviesTable);
            //initializes the filter view
            uiInitializer.initializeSearchView(isearchGraphic, searchButton, searchValue, infoEmptyLabel);
            //bind elapsed time to the duration
            uiInitializer.initializeDurationLabels(timeElapsed,totalTime,playerCommander);
            //bind model to total duration
            uiInitializer.initializeTotalDurationModel(model,playerCommander);
            //initialize volumeSlider
            uiInitializer.initializeVolumeSlider(volumeSlider,model);
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
            loader.setController(new NewCategoryControllerRel());
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
            CategoryReloadable categoryReloadable = CategoryReloadableHandler.getInstance(this.model);
           FXMLLoader loader = new FXMLLoader(getClass().getResource("../components/newEditDeleteCategory/NewCategoryView.fxml"));
            loader.setController(new EditCategoryController());
            Parent root = loader.load();
            EditCategoryController edc= loader.getController();
            edc.setReloadable(categoryReloadable);
            edc.setTextFieldText(categoryToUpdate);
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

    public void deleteCategory(ActionEvent actionEvent){
        Category categoryToDelete = getSelectedCategory();
        if (categoryToDelete == null){
            ExceptionHandler.displayWarningAlert(InformationalMessages.NO_CATEGORY_SELECTED, "No category has been selected");
            return;
        }
        if (isCategoryCurrentlyOpen(categoryToDelete)){
            ExceptionHandler.displayInformationAlert(InformationalMessages.CATEGORY_IN_USE, null);
            return;
        }
        DeleteCategoryController deleteCategoryController = createDeleteCategoryController(categoryToDelete);
        if (deleteCategoryController.getConfirmationWindow() != null){
            Stage confirmationStage = createConfirmationStage(deleteCategoryController, actionEvent);
            confirmationStage.show();
        } else {
            ExceptionHandler.displayErrorAlert(InformationalMessages.OPERATION_FAILED, null);
        }
    }

    private boolean isCategoryCurrentlyOpen(Category categoryToDelete) {
        return this.model.checkOpenCategory(categoryToDelete);
    }

    private DeleteCategoryController createDeleteCategoryController(Category categoryToDelete) {
        DeleteCategoryController deleteCategoryController = new DeleteCategoryController();
        deleteCategoryController.getCategoryToDelete(categoryToDelete);
        deleteCategoryController.initialize(null, null);
        deleteCategoryController.setReloadable(categoryReloadable);
        return deleteCategoryController;
    }

    /**
     * Creates a confirmation stage for deleting a category.
     * @param deleteCategoryController The controller handling the category deletion.
     * @param actionEvent The ActionEvent for triggering the operation.
     * @return The created confirmation stage.
     */
    private Stage createConfirmationStage(DeleteCategoryController deleteCategoryController, ActionEvent actionEvent) {
        Stage mainStage = Utility.getCurrentStage(actionEvent);
        Scene scene = new Scene(deleteCategoryController.getConfirmationWindow());
        return Utility.createPopupStage(mainStage, scene, Titles.DELETE_CATEGORY.getValue(), POPUP_WIDTH);
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

    public void playNextMovie(ActionEvent event) {
    }


    public void editMovie(ActionEvent event) {
        String resourcePath=  "../components/newEditDeleteMovies/EditMovieView.fxml";
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        if(selectedMovie==null){
            ExceptionHandler.displayInformationAlert(InformationalMessages.NO_MOVIE_OPENED,"Please open a movie");
            return;
        }
        try {
            FXMLLoader loader =new FXMLLoader(getClass().getResource(resourcePath));
            Parent root = loader.load();
            EditMovieController editMovie = loader.getController();
            editMovie.getCurrentOpenedCategory(model.getCurrentOpenedCategory());
            editMovie.setModel(this.model);
            System.out.println(selectedMovie.getName());
            editMovie.setTextFieldText(selectedMovie);
            Scene scene = new Scene(root);
            Stage mainStage = Utility.getCurrentStage(event);
            Stage newCategoryStage = Utility.createPopupStage(mainStage, scene, Titles.ADD_NEW_MOVIE.getValue(),POPUP_WIDTH);
            newCategoryStage.show();
        } catch (IOException e) {
            ExceptionHandler.displayErrorAlert(InformationalMessages.FXML_MISSING, "Application error");
        }
    }

    public void deleteMovie(ActionEvent event) {
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        if(selectedMovie==null){
            ExceptionHandler.displayInformationAlert(InformationalMessages.NO_MOVIE_OPENED,"Please open a movie");
            return;
        }
        if(model.isMoviePlaying(selectedMovie)){
            ExceptionHandler.displayInformationAlert(InformationalMessages.MOVIE_IN_USE,"Delete forbidden");
            return;
        }
        MovieReloader movieReloader =  new MovieReloader(model);
        DeleteMovieController deleteMovieController = new DeleteMovieController(movieReloader);
        deleteMovieController.setMovieToDelete(selectedMovie);
        deleteMovieController.initialize(null,null);
        if (deleteMovieController.getConfirmationWindow() != null) {
            Stage mainStage = Utility.getCurrentStage(event);
            Scene scene = new Scene(deleteMovieController.getConfirmationWindow());
             Stage stage=Utility.createPopupStage(mainStage, scene, Titles.DELETE_MOVIE.getValue(), POPUP_WIDTH);
              stage.show();
        } else {
            ExceptionHandler.displayErrorAlert(InformationalMessages.OPERATION_FAILED,null);
        }


    }
}
