package gui.components.movies;

import gui.components.listeners.MovieSelectionListener;

public class MovieSelectionHandler implements MovieSelectionListener {
    @Override
    public void playSelectedMovie(int id, boolean play) {
        System.out.println(id);
    }
}
