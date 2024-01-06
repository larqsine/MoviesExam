package gui.components.movies;

import gui.components.listeners.MovieSelectionListener;

public class MovieSelectionHandler implements MovieSelectionListener {
    @Override
    public void playSelectedMovie(String title, boolean play) {
        System.out.println(title);
    }
}
