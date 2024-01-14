package gui.components.player;


import exceptions.MoviesException;
import gui.components.listeners.DataSupplier;
import gui.components.listeners.MediaViewReloader;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import utility.ExceptionHandler;
import utility.ExceptionsMessages;
import utility.Operations;

public class Player implements PlayerControl {
    private MediaPlayer mediaPlayer;
    private static Player instance;
    private Media movie;
    private DataSupplier dataSupplier;
    private MediaViewReloader mediaViewReloader;
    private final StringProperty time = new SimpleStringProperty();

    private BooleanProperty isMute;


    private Player(DataSupplier dataSupplier,MediaViewReloader mediaViewReloader) throws MoviesException {
        this.dataSupplier = dataSupplier;
        this.mediaViewReloader = mediaViewReloader;
        checkMediaValid(dataSupplier.getMedia(Operations.GET_DEFAULT));
        playTrack(dataSupplier.isPlaying());
    }

    public static Player useMediaPlayer(DataSupplier dataSupplier,MediaViewReloader mediaViewReloader) throws MoviesException {
        if (instance == null) {
            instance = new Player(dataSupplier, mediaViewReloader);
        }
        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * controls the play functionality if the app just started , music will not play
     **/
    private MediaPlayer playTrack(boolean play) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer.volumeProperty().unbind();
        }
        Media media = this.movie;
        mediaPlayer = new MediaPlayer(media);
        mediaViewReloader.getUpdatedMedia(mediaPlayer);
        mediaPlayer.volumeProperty().bind(Bindings.when(dataSupplier.isMute())
                .then(0.0)
                .otherwise(dataSupplier.getVolumeObservable()));
        bindDurationToLabel(time);
//        mediaPlayer.setOnEndOfMedia(this::playContinuous);
        if (play) {
            mediaPlayer.play();
        }
        return mediaPlayer;
    }

    private void playNextTrack(boolean play) {
        this.mediaPlayer = playTrack(play);
    }


    private void playContinuous() {
        playCurrent(dataSupplier.getMedia(Operations.GET_NEXT), dataSupplier.isPlaying());
    }

    private void bindDurationToLabel(StringProperty stringToBind) {
        StringBinding currentTimeStringBinding = Bindings.createStringBinding(() -> formatDuration(mediaPlayer.getCurrentTime()), mediaPlayer.currentTimeProperty());
        stringToBind.bind(currentTimeStringBinding);
    }

    private String formatDuration(Duration duration) {
        int hours = (int) duration.toHours();
        int minutes = (int) (duration.toMinutes() % 60);
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void checkMediaValid(Media media) throws MoviesException {
        if (media == null) {
            throw new MoviesException(ExceptionsMessages.READING_SONG_LOCAL);
        } else {
            this.movie = media;
        }
    }


    public void setMovie(Media media) throws MoviesException {
        checkMediaValid(media);
    }

    public void setDataSupplier(DataSupplier dataSupplier) {
        this.dataSupplier = dataSupplier;
    }


    @Override
    public void play() {
        this.mediaPlayer.play();
    }

    @Override
    public void pause() {
        this.mediaPlayer.pause();
    }

    @Override
    public void playNext(Media media, boolean play) {
        try {
            setMovie(media);
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage(),null);
            return;
        }
        playNextTrack(play);
    }

    @Override
    public void playPrevious(Media media, boolean play) {
        try {
            setMovie(media);
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage(),null);
            return;
        }
        playNextTrack(play);
    }

    @Override
    public Duration getCurrentTime() {
        return getMediaPlayer().getCurrentTime();
    }

    @Override
    public void playCurrent(Media media, boolean play) {
        try {
            checkMediaValid(media);
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage(),null);
            return;
        }
        playTrack(play);
    }

    @Override
    public void bindMediaTimeToScreen(Label label) {
        label.textProperty().bind(time);
    }
}