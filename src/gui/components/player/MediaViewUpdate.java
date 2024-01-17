package gui.components.player;

import gui.MainView.MainModel;
import gui.components.listeners.MediaViewReloader;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaViewUpdate implements MediaViewReloader {
    private final MediaView mediaView;
    private final Slider timeSlider;
    private MediaPlayer currentMediaPlayer;
    private ChangeListener<Duration> currentTimeListener;

    public MediaViewUpdate(MediaView mediaView, Slider slider) {
        this.mediaView = mediaView;
        this.timeSlider = slider;
        initializeCurrentTimeListener();
    }

    private void initializeCurrentTimeListener() {
        currentTimeListener = (obs, oldVal, newVal) -> {
            if (!timeSlider.isValueChanging()) {
                timeSlider.setValue(newVal.toSeconds());
            }
        };
    }

    @Override
    public void updateMediaView(MediaPlayer mediaPlayer) {
        this.mediaView.setMediaPlayer(mediaPlayer);
        updateSliderMedia(mediaPlayer);
    }

    public void updateSliderMedia(MediaPlayer mediaPlayer) {
        if (currentMediaPlayer != null) {
            currentMediaPlayer.currentTimeProperty().removeListener( currentTimeListener);
        }

        // Update the reference to the new MediaPlayer
        currentMediaPlayer = mediaPlayer;

        // Add the listener to the new MediaPlayer for updating the slider
        currentMediaPlayer.currentTimeProperty().addListener( currentTimeListener);

        // Add listeners for user interaction with the slider
        timeSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging) {
                currentMediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
            }
        });

        timeSlider.setOnMouseReleased(event -> {
            currentMediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
        });
    }
}



