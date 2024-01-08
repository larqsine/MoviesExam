package utility;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Utility {

    public static String convertSecondsToStringRepresentation(double length) {
        if (length == 0.0) {
            return String.format("%02d:%02d:%02d", 0, 0, 0);
        }
        int hours = (int) length / 3600;
        int minutes = (int) (length / 60);
        int seconds = (int) (length % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    public static Stage getCurrentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public static double calculateMidPoint(double mainX,double mainW,double popW){
     return mainX+(mainW/2)-(popW/2);
    }
    /**
     * initiate a  new stage that will block all the events, besides the current window
     */
    public static Stage createPopupStage(Stage mainStage, Scene scene, String name, int popWidth) {
        Stage newSongStage = new Stage();
        newSongStage.setX(Utility.calculateMidPoint(mainStage.getX(), mainStage.getWidth(), popWidth));
        newSongStage.setY(mainStage.getHeight() / 2);
        newSongStage.setTitle(name);
        newSongStage.setScene(scene);
        newSongStage.initModality(Modality.WINDOW_MODAL);
        newSongStage.initOwner(mainStage);
        return newSongStage;
    }




}
