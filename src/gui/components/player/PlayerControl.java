package gui.components.player;


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


}
