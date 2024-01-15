//package gui.components.movies;
//
//import exceptions.MoviesException;
//import gui.MainView.MainModel;
//import gui.MainView.MainViewController;
//import gui.components.listeners.MovieSelectionListener;
//import gui.components.player.PlayerCommander;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import utility.Operations;
//import utility.PlayButtonGraphic;
//
//public class MovieSelectionHandler implements MovieSelectionListener {
//    private final MainModel model;
//    private final PlayerCommander playerCommander;
//
//    public MovieSelectionHandler(MainModel model, PlayerCommander playerCommander) {
//        this.model = model;
//        this.playerCommander = playerCommander;
//    }
//    /**
//     * Play the selected movie when it is double-clicked.
//     *
//     * @param id   The index of the selected song.
//     * @param play    Whether to play the song or not.
//     */
//    @Override
//    public void playSelectedMovie(int id, boolean play)  {
//        setMovieToPlay(id,play);
//        playerCommander.processOperation(Operations.PLAY_CURRENT);
//    }
//    private void setMovieToPlay(int id,boolean play) {
//        model.setObservablePlayPropertyValue(play);
//       model.setCurrentPlayingMovie(id);
//    }
//}
