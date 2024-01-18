package gui.components.newEditDeleteMovies;
import gui.components.listeners.MovieReloadable;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.ExceptionHandler;
import utility.InformationalMessages;

public abstract class NewEditController {
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

    public boolean validateInputs(String title , String path, MovieModel movieModel) {
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
}
