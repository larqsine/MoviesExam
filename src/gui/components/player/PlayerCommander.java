package gui.components.player;

import exceptions.MoviesException;
import gui.components.listeners.DataSupplier;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;
import utility.Operations;


public class PlayerCommander {
    private final PlayerControl playerControl;
    private final DataSupplier dataSupplier;

    public PlayerCommander(DataSupplier dataSupplier, PlayerControl playerControl) throws MoviesException {
        this.dataSupplier = dataSupplier;
        this.playerControl = playerControl;
    }

    public void processOperation(Operations operation) {
        switch (operation) {
            case Operations.PLAY_NEXT:
                playerControl.playNext(dataSupplier.getMedia(Operations.GET_NEXT), dataSupplier.isPlaying());
                break;
            case Operations.PLAY_PREVIOUS:
                playerControl.playNext(dataSupplier.getMedia(Operations.GET_PREVIOUS), dataSupplier.isPlaying());
                break;
            case Operations.PAUSE:
                playerControl.pause();
                break;
            case PLAY_CURRENT:
                playerControl.playCurrent(dataSupplier.getMedia(Operations.GET_CURRENT_MOVIE), dataSupplier.isPlaying());
                break;
            default:
                playerControl.play();
        }
    }

    public Duration getCurrentTime() {
        return playerControl.getCurrentTime();
    }

    public void bindMediaTimeToScreen(Label label) {
        playerControl.bindMediaTimeToScreen(label);
    }

    public void bindTotalTimeToScreen(Label label) {
        playerControl.bindTotalMediaToScreen(label);
    }


    public void bindTotalDurationToModel(DoubleProperty totalDuration) {
        playerControl.bindTotalDurationToModel(totalDuration);
    }

}
