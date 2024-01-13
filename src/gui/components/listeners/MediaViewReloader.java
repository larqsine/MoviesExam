package gui.components.listeners;

import javafx.event.ActionEvent;
import javafx.scene.media.MediaPlayer;

public interface MediaViewReloader {
  MediaViewReloader getReloadable();

  void getUpdatedMedia(MediaPlayer instance);

  void saveMovieEdit(ActionEvent event);

  void cancelMovieEdit(ActionEvent event);

  void reloadMovies();

}
