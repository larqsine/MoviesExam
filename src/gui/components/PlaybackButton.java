package gui.components;
import gui.MainView.MainModel;
import gui.components.listeners.PlaybackObserver;
import gui.components.player.PlayerCommander;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import utility.GraphicIdValues;
import utility.Operations;

public class PlaybackButton extends Button implements PlaybackObserver {
    private static final String PLAY_ICON_PATH = "M0 0 L0 10 L10 5 z";
    private static final String PAUSE_ICON_PATH = "M0 0h3v10H0zM5 0h3v10H5z";
   private PlayerCommander playerCommander;
   private MainModel model;

    public PlaybackButton(int width,int height,PlayerCommander playerCommander,MainModel model) {
        this.playerCommander=playerCommander;
        this.model=model;
        this.setOnAction((event -> buttonAction(model.getObservablePlayPropertyValue())));
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setGraphic(playSvg());
        this.setId("playButton");
        model.addObserver(this);
    }
    @Override
    public void playbackChange(boolean newValue) {
      changeGraphic(newValue);
    }
    private SVGPath playSvg() {
        SVGPath playIcon = new SVGPath();
        playIcon.setContent(PLAY_ICON_PATH);
        playIcon.setFill(Color.BLACK);
        playIcon.setId(GraphicIdValues.PLAY.getValue());
        return playIcon;
    }
    private SVGPath pauseSvg() {
        SVGPath pauseIcon = new SVGPath();
        pauseIcon.setContent(PAUSE_ICON_PATH);
        pauseIcon.setFill(Color.BLACK);
        pauseIcon.setId(GraphicIdValues.PAUSE.getValue());
        return pauseIcon;
    }

private void buttonAction(boolean value){
    if(value){
        this.playerCommander.processOperation(Operations.PAUSE);
        model.setObservablePlayPropertyValue(false);
    }
    else{
        this.playerCommander.processOperation(Operations.PLAY);
        model.setObservablePlayPropertyValue(true);
    }
}

private void changeGraphic(boolean value){
        this.setGraphic(value?pauseSvg():playSvg());
}

}
