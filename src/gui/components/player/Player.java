package gui.components.player;
import exceptions.MoviesException;
import gui.components.listeners.DataSupplier;
import gui.components.listeners.MediaViewReloader;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
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
    private final StringProperty totalTime = new SimpleStringProperty();
    private final DoubleProperty currentDuration = new SimpleDoubleProperty();
    private final DoubleProperty totalDuration = new SimpleDoubleProperty();
    private BooleanProperty isMute;


    private Player(DataSupplier dataSupplier, MediaViewReloader mediaViewReloader) throws MoviesException {
        this.dataSupplier = dataSupplier;
        this.mediaViewReloader = mediaViewReloader;
        checkMediaValid(dataSupplier.getMedia(Operations.GET_DEFAULT));
        playTrack(dataSupplier.isPlaying());
    }

    public static Player useMediaPlayer(DataSupplier dataSupplier, MediaViewReloader mediaViewReloader) throws MoviesException {
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
        updateMediaView();
        mediaPlayer.volumeProperty().bind(Bindings.when(dataSupplier.isMute())
                .then(0.0)
                .otherwise(dataSupplier.getVolumeObservable()));
        bindDurationToLabel(time);
        bindTotalDurationToLabel(totalTime);
        bindCurrentDuration(currentDuration);
        bindTotalDuration(totalDuration);
//        mediaPlayer.setOnEndOfMedia(this::playContinuous);
        if (play) {
            mediaPlayer.play();
        }
        return mediaPlayer;
    }

    private void updateMediaView() {
        mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == MediaPlayer.Status.READY) {

                mediaViewReloader.getMediaViewPlayable();
                mediaViewReloader.getUpdatedMedia(mediaPlayer);
            }
        });
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

    private void bindTotalDurationToLabel(StringProperty stringToBind) {
        mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == MediaPlayer.Status.READY) {
                Duration totalDuration = mediaPlayer.getTotalDuration();
                stringToBind.bind(Bindings.createStringBinding(
                        () -> formatDuration(totalDuration)
                ));
            }
        });
    }


    private void bindCurrentDuration(DoubleProperty doubleProperty) {
        doubleProperty.bind(Bindings.createDoubleBinding(
                () -> mediaPlayer.getCurrentTime().toSeconds(),
                mediaPlayer.currentTimeProperty()
        ));
    }

    private void bindTotalDuration(DoubleProperty doubleProperty) {
        getMediaPlayer().statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == MediaPlayer.Status.READY) {
                doubleProperty.bind(Bindings.createDoubleBinding(
                        () -> mediaPlayer.getTotalDuration().toSeconds(),
                        mediaPlayer.totalDurationProperty()
                ));
            }
        });
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
            ExceptionHandler.displayErrorAlert(e.getMessage(), null);
            return;
        }
        playNextTrack(play);
    }

    @Override
    public void playPrevious(Media media, boolean play) {
        try {
            setMovie(media);
        } catch (MoviesException e) {
            ExceptionHandler.displayErrorAlert(e.getMessage(), null);
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
            ExceptionHandler.displayErrorAlert(e.getMessage(), null);
            return;
        }
        playTrack(play);
    }

    @Override
    public void bindMediaTimeToScreen(Label label) {
        label.textProperty().bind(time);
    }

    @Override
    public void bindTotalMediaToScreen(Label label) {
        label.textProperty().bind(totalTime);
    }

    @Override
    public void bindDurationToModel(DoubleProperty duration) {
        duration.bind(currentDuration);
    }

    @Override
    public void bindTotalDurationToModel(DoubleProperty totalDuration) {
        totalDuration.bind(this.totalDuration);
    }


}