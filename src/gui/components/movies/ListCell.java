package gui.components.movies;

import be.Genre;
import javafx.geometry.Pos;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

public class ListCell<T> extends MovieCell<List<Genre>>{
    public ListCell(int width,int height, Duration duration) {
    super(width,height,duration);
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
            setGraphic(createMovieCell(convertToString(item)));
            setAlignment(Pos.CENTER);
        }
    }

    private String convertToString(List<Genre> items) {
        return items.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(","));
    }

}
