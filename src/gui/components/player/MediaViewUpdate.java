package gui.components.player;

import gui.components.listeners.MediaViewReloader;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MediaViewUpdate implements MediaViewReloader {
    private MediaView mediaView;
    private HBox mediaContainer;
    public MediaViewUpdate(MediaView mediaView , HBox mediaContainer) {
        this.mediaContainer=mediaContainer;
        this.mediaView = mediaView;
    }


    @Override
    public void getUpdatedMedia(MediaPlayer instance) {
        this.mediaView.setMediaPlayer(instance);
    }

    public void getMediaViewPlayable(MediaView mediaView){
      this.mediaContainer.getChildren().add(mediaView);
    }


}
