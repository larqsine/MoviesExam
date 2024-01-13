package gui.components.newEditDeleteMovies;

import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.components.listeners.MovieReloadable;

public class MovieReloader implements MovieReloadable {
    private MainModel model;

    public MovieReloader(MainModel model) {
        if(model!=null){
            this.model=model;
        }
    }

    @Override
    public void reloadMovies() throws MoviesException {
        model.reloadMoviesFromDB();
    }

    public MainModel getModel() {
        return model;
    }

    public void setModel(MainModel model) {
        this.model = model;
    }
}
