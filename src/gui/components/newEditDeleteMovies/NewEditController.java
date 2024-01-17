package gui.components.newEditDeleteMovies;
import be.Category;
import be.Movie;
import gui.components.listeners.MovieReloadable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.ExceptionHandler;
import utility.InformationalMessages;


public abstract class NewEditController  {
    private MovieReloadable reloadable;
    private final FileChooser fileChooser = new FileChooser();
    private int openedCategory;



    /**
     * returns the current opened category*/
    public int getOpenedCategory() {
        return openedCategory;
    }
/**
 * sets the current opened category*/
    public void setOpenedCategory(int openedCategory) {
        this.openedCategory = openedCategory;
    }

    /**
     * Abstract method to cancel movie creation/editing.
     *
     * @param event The ActionEvent that triggered the cancel operation.
     */
    public abstract void cancelAddEditMovie(ActionEvent event);

    /**
     * Abstract method to save/update a movie.
     *
     * @param event The ActionEvent that triggered the save/update.
     */
    public abstract void saveAddEditMovie(ActionEvent event);

    public abstract void openFileChooser(ActionEvent event);

    public FileChooser getFileChooser() {
        setDefaultExtensions();
        return fileChooser;
    }

    private void setDefaultExtensions() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Mp4 Files", "*.mp4")
                , new FileChooser.ExtensionFilter("Mpeg4 Files", "*.mpeg4")
        );
    }
    public void setReloadableController(MovieReloadable reloadable) {
        this.reloadable = reloadable;
    }

    public MovieReloadable getReloadableController() {
        return reloadable;
    }

    public boolean validateInputs(String title , String path,MovieModel movieModel) {
        if (movieModel.areTitleOrPathEmpty(title, path)) {
            ExceptionHandler.displayInformationAlert(InformationalMessages.NO_EMPTY_INPUT,"No empty input");
            return true;
        }
        if (!movieModel.checkIfFileExists(path)) {
            ExceptionHandler.displayInformationAlert(InformationalMessages.NO_FILE,"Nonexistent file");
            return true;
        }
        return false;
    }
    public void  closeStage(Stage stage ){
        stage.close();
    }










//    Salma code



//
//    @Override
//    public MediaViewReloader getReloadable() {
//        return null;
//    }
//
//    @Override
//    public void getUpdatedMedia(MediaPlayer instance) {
//
//    }
//
//    @Override
//    public void saveMovieEdit(ActionEvent event) {
//        String title = getMovieTitle().getText();
//        if (movieModel.checkTitle(title)) {
//            showTitleError();
//        } else {
//            reloadMovies();
//            closeCurrentStage(event);
//        }
//    }
//
//    public void closeCurrentStage(ActionEvent event) {
//    }
//
//    @Override
//    public void cancelMovieEdit(ActionEvent event) {
//        closeCurrentStage(event);
//    }
//
    public void setOnChangeListener(TextField textField, Label label) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (label.isVisible()) {
                label.setVisible(false);
            }
        });
    }
//
//    public void showTitleError() {
//        getInformation().setText(InformationalMessages.NO_EMPTY_TITLE.getValue());
//        getInformation().setVisible(true);
//    }
//
//    public Stage getCurrentStage(ActionEvent event) {
//        return (Stage) ((Node) event.getSource()).getScene().getWindow();
//    }
//
//
//    public TextField getMovieTitle() {
//        return movieTitle;
//    }
//
//    public Label getInformation() {
//        return information;
//    }
//
//    public Button getSaveUpdateButton() {
//        return saveUpdateButton;
//    }
//
//    @Override
//    public void reloadMovies() {
//
//    }


}
