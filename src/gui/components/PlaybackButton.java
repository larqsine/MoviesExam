package gui.components;
import gui.MainView.MainModel;
import gui.components.listeners.PlaybackObserver;
import gui.components.player.PlayerCommander;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
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
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setRadius(10);
        innerShadow.setColor(javafx.scene.paint.Color.web("#8C2F2F"));
        innerShadow.setOffsetX(5);
        innerShadow.setOffsetY(5);
        this.setEffect(innerShadow);
        this.setStyle("-fx-background-color: #F26F63; -fx-background-radius: 30; -fx-transition: background-color 0.3s, -fx-effect 0.3s, -fx-scale-x 0.3s, -fx-scale-y 0.3s;");
        this.setOnMouseEntered(e -> setHoverEffect(true));
        this.setOnMouseExited(e -> setHoverEffect(false));
        model.addObserver(this);
    }
    @Override
    public void playbackChange(boolean newValue) {
      changeGraphic(newValue);
    }
    private SVGPath playSvg() {
        SVGPath playIcon = new SVGPath();
        playIcon.setContent(PLAY_ICON_PATH);
        playIcon.setFill(Color.WHITE);
        return playIcon;
    }
    private SVGPath pauseSvg() {
        SVGPath pauseIcon = new SVGPath();
        pauseIcon.setContent(PAUSE_ICON_PATH);
        pauseIcon.setFill(Color.WHITE);
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

private void setHoverEffect(boolean isHovered) {
        if (isHovered) {
            // Apply hover effects
            this.setStyle("-fx-background-color: #F26F63; -fx-background-radius: 30; -fx-effect: innershadow(gaussian, #8C2F2F, 15, 0, 8, 8);");
            this.setScaleX(1.1);
            this.setScaleY(1.1);
        } else {
            // Remove hover effects
            this.setStyle("-fx-background-color: #F26F63; -fx-background-radius: 30;");
            this.setScaleX(1.0);
            this.setScaleY(1.0);
        }
    }

}
