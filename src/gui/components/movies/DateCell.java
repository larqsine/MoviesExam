package gui.components.movies;

import javafx.util.Duration;

import java.util.Date;

public class DateCell<T> extends MovieCell<Date> {
    public DateCell(int width, Duration duration) {
        this.setCellWidth(width);
        if (duration != null) {
            this.setDeelayDuration(duration);
        }
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
            setGraphic(createSongCell(convertDateToString(item)));
        }
    }

    private String convertDateToString(Date date) {
        return date.toString();
    }

}
