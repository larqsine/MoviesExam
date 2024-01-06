package gui.components.listeners;

import be.Movie;
import javafx.collections.ObservableList;

public interface DataSupplier {
    void supplyTableData(ObservableList<Movie> movies);
}
