package gui.components.movies;
import javafx.util.Duration;
public class DoubleCell<T> extends MovieCell<Double> {

    public DoubleCell(int width, Duration duration) {
        this.setCellWidth(width);
        if (duration != null) {
            this.setDeelayDuration(duration);
        }
    }
    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setOnMouseEntered(null);
            setOnMouseExited(null);
        } else {
            setGraphic(createSongCell(castToString(item)));
        }
    }

    private String castToString(Double item) {
        return String.valueOf(item);
    }
}
