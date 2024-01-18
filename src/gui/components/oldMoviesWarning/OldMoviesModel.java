package gui.components.oldMoviesWarning;
import bll.movieLogic.MovieLogic;
import bll.movieLogic.MovieLogicAPI;
import exceptions.MoviesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OldMoviesModel {
    private final MovieLogicAPI movieLogic;
    private final ObservableList<String> movieNames;

    private static OldMoviesModel instance;

    private OldMoviesModel() throws MoviesException {
        this.movieLogic = new MovieLogic();
        this.movieNames= FXCollections.observableArrayList();
        initializeList();
    }


    public static OldMoviesModel getInstance() throws MoviesException {
        if(instance==null){
            instance=new OldMoviesModel();
        }
        return instance;
    }

    public ObservableList<String> getOldMovies(){
        return this.movieNames;
    }

    private void initializeList() throws MoviesException {
        this.movieNames.setAll(movieLogic.oldMovies());
    }

   public boolean isListEmpty(){
        return this.movieNames.isEmpty();
   }

}
