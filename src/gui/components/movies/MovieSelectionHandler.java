package gui.components.movies;

import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.MainView.MainViewController;
import gui.components.listeners.MovieSelectionListener;
import gui.components.player.PlayerCommander;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utility.Operations;
import utility.PlayButtonGraphic;

public class MovieSelectionHandler implements MovieSelectionListener {

    private MainModel model;
    private final PlayerCommander playerCommander;
    @FXML
    private Button button;

    public MovieSelectionHandler(MainModel model, PlayerCommander playerCommander, Button button) {
        this.model = model;
        this.playerCommander = playerCommander;
        this.button = button;
    }


    /**
     * Play the selected movie when it is double-clicked.
     *
     * @param id   The index of the selected song.
     * @param play    Whether to play the song or not.
     */

    @Override
    public void playSelectedMovie(int id, boolean play)  {
        setMovieToPlay(id,play);
        playerCommander.processOperation(Operations.PLAY_CURRENT);
        changeButtonGraphic(this.button);
    }

    private void setMovieToPlay(int id,boolean play) {model.setPlayMovie(play);
       model.setCurrentPlayingMovie(id);
    }

    private void changeButtonGraphic(Button button) {
        if (isGraphicPlaying(button)) {
            button.setText(PlayButtonGraphic.STOP.getValue());
        }
    }

    private boolean isGraphicPlaying(Button button) {
        return button.getText().equals(PlayButtonGraphic.PLAY.getValue());
    }


}
