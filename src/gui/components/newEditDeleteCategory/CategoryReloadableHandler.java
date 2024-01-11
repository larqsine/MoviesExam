package gui.components.newEditDeleteCategory;
import exceptions.MoviesException;
import gui.MainView.MainModel;
import utility.ExceptionHandler;
import utility.ExceptionsMessages;

public class CategoryReloadableHandler implements CategoryReloadable {
    private MainModel model;
    private static CategoryReloadableHandler instance;
    private CategoryReloadableHandler(MainModel model){
        this.model=model;
    }
    public static  CategoryReloadableHandler getInstance(MainModel model){
        if(instance==null){
            instance=new CategoryReloadableHandler(model);
        }
        return instance;
    }
    @Override
    public void reloadCategoriesFromDB() {
        try {
            model.reloadSongsFromDB();
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(ExceptionsMessages.READING_FROMDB_FAILED,null);
        }
    }

    @Override
    public void reloadMovies() {

    }
}
