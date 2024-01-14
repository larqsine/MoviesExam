package gui.components.movies;

import javafx.geometry.Pos;
import javafx.util.Duration;

import java.util.Date;

public class DateCell<T> extends MovieCell<Date> {
    public DateCell(int width,int height, Duration duration) {
        super(width,height,duration);
    }

    @Override
    protected void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setOnMouseEntered(null);
            setOnMouseExited(null);
        } else {
            setGraphic(createMovieCell(convertDateToString(item)));
            setAlignment(Pos.CENTER);
        }
    }

    private String convertDateToString(Date date) {
        return date.toString();
    }

}
