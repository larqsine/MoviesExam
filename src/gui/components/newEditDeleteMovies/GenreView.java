package gui.components.newEditDeleteMovies;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class GenreView extends ListView<String> {

    public GenreView() {
        this.setCellFactory((cell) -> new GenreOptions());
        this.setMaxHeight(200);
        this.setMaxWidth(200);

    }

    public void setGenres(ObservableList<String> genres) {
        this.setItems(genres);
    }

}
