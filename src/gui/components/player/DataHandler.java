package gui.components.player;

import gui.MainView.MainModel;
import gui.components.listeners.DataSupplier;
import gui.playOperations.PlayOperations;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.scene.media.Media;
import utility.Operations;

public class DataHandler implements DataSupplier {
    private MainModel model;
    private static DataHandler instance;
    private PlayOperations playOperations;
    public static DataHandler getInstance(MainModel model, PlayOperations playOperationsHandler){
        if(instance==null){
            instance=new DataHandler(model,playOperationsHandler);
        }
        return instance;
    }

    private  DataHandler (MainModel model,PlayOperations playOperationsHandler){
        this.model=model;
        this.playOperations=playOperationsHandler;
    }


    /**
     * provides the media player with the necessary data
     *
     * @param operation the operation that needs to be performed, play next, play previous , play initial song
     */
    @Override
    public Media getMedia(Operations operation) {
        return playOperations.getMedia(operation);
    }


    /**
     * controls iff the song can be played or not
     */
    @Override
    public boolean isPlaying() {
        return this.model.getObservablePlayPropertyValue();
    }



    /**
     * binds the volume off the player with the volume level stored in the model
     */
    @Override
    public DoubleProperty getVolumeObservable() {
        return this.model.volumeLevelProperty();
    }


        /**
     * supplies data to the player when the volume is mute
     */
    @Override
    public BooleanProperty isMute() {
        return this.model.isMuteProperty();
    }
}
