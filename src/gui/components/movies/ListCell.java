package gui.components.movies;

import be.Category;
import be.Genre;
import javafx.util.Duration;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ListCell<T> extends MovieCell<List<Genre>>{
    public ListCell(int width, Duration duration) {
        this.setCellWidth(width);
        if (duration != null) {
            this.setDeelayDuration(duration);
        }
    }

    @Override
    protected void updateItem(List<Genre> item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setOnMouseEntered(null);
            setOnMouseExited(null);
        } else {
            setGraphic(createSongCell(convertToString(item)));
        }
    }

    private String convertToString(List<Genre> items) {
        return items.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(","));
    }

}
