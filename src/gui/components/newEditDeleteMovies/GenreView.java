package gui.components.newEditDeleteMovies;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class GenreView extends ListView<String> implements CheckListener {

    private final List<String> selectedGenres;

    public GenreView() {
        this.selectedGenres = new ArrayList<>();
        this.setCellFactory((cell) -> new GenreOptions(this));
        this.setMaxHeight(200);
        this.setMaxWidth(200);
    }

    public void setGenres(ObservableList<String> genres) {
        this.setItems(genres);
    }


    @Override
    public void getSelectedItem(String selected) {
        this.selectedGenres.add(selected);
    }

    @Override
    public void removeUnselectedItem(String selected) {
        this.selectedGenres.remove(selected);
    }

    public List<String> getSelectedGenres() {
        return selectedGenres;
    }
}
