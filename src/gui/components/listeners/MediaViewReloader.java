package gui.components.listeners;

import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public interface MediaViewReloader {
  void getUpdatedMedia(MediaPlayer instance);
  void getMediaViewPlayable(MediaView mediaView);
}
