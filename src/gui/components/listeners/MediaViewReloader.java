package gui.components.listeners;

import javafx.event.ActionEvent;
import javafx.scene.media.MediaPlayer;

public interface MediaViewReloader {
  void getUpdatedMedia(MediaPlayer instance);
}
