package gui.components.player;

import gui.components.listeners.MediaViewReloader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.util.List;

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
      List<Node> childrens = this.mediaContainer.getChildren();
      if(!childrens.isEmpty()){
          childrens.remove(0);
      }
      this.mediaContainer.getChildren().add(mediaView);
    }


}
