package gui.components.listeners;

import be.Movie;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import utility.Operations;

public interface DataSupplier {
    Media getMedia(Operations operation);

    boolean isPlaying();

    DoubleProperty getVolumeObservable();

    BooleanProperty isMute();


}
