package gui.components.player;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public interface PlayerControl {
    void play();
    void pause();
    void playNext(Media media, boolean play);
    void playPrevious(Media media,boolean play);
    Duration getCurrentTime();
    void playCurrent(Media media,boolean play);

    void bindMediaTimeToScreen(Label label);
    MediaPlayer getMediaPlayer();
    void bindTotalMediaToScreen(Label label);
    void bindDurationToModel(DoubleProperty duration);
    void bindTotalDurationToModel(DoubleProperty totalDuration);


}
