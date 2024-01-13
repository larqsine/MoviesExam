package gui.components.player;

import gui.components.listeners.MediaViewReloader;
import javafx.event.ActionEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MediaViewUpdate implements MediaViewReloader {
    private MediaView mediaView;
    public MediaViewUpdate(MediaView mediaView) {
        this.mediaView = mediaView;
    }

    @Override
    public MediaViewReloader getReloadable() {
        return null;
    }

    @Override
    public void getUpdatedMedia(MediaPlayer instance) {
        this.mediaView.setMediaPlayer(instance);
    }

    @Override
    public void saveMovieEdit(ActionEvent event) {

    }

    @Override
    public void cancelMovieEdit(ActionEvent event) {

    }

    @Override
    public void reloadMovies() {

    }
}
