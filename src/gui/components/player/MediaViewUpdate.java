package gui.components.player;

import gui.components.listeners.MediaViewReloader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.util.List;

public class MediaViewUpdate implements MediaViewReloader {
    private MediaView mediaView;
    public MediaViewUpdate(MediaView mediaView) {
        this.mediaView=mediaView;

    }
    @Override
    public void updateMediaView(MediaPlayer mediaPlayer){
        this.mediaView.setMediaPlayer(mediaPlayer);
    }

}
