package gui.components.newEditDeleteMovies.genreView;

import gui.components.newEditDeleteMovies.CheckListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceView extends ListView<String> implements CheckListener {
    private final List<String> selectedElements;
    public MultipleChoiceView() {
        this.selectedElements = new ArrayList<>();
        this.setCellFactory((cell) -> new MultipleChoiceViewOptions(this));
        this.setMaxHeight(150);
        this.setMaxWidth(200);
    }

    public void setElements(ObservableList<String> genres) {
        this.setItems(genres);
    }


    @Override
    public void getSelectedItem(String selected) {
        this.selectedElements.add(selected);
    }

    @Override
    public void removeUnselectedItem(String selected) {
        this.selectedElements.remove(selected);
    }

    public List<String> getSelectedElements() {
        return selectedElements;
    }


}
