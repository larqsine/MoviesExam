package gui.components.movies;
import javafx.geometry.Pos;
import javafx.util.Duration;
public class DoubleCell<T> extends MovieCell<Double> {

    public DoubleCell(int width,int height, Duration duration) {
        super(width,height,duration);
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
            setGraphic(createMovieCell(castToString(item)));
            setAlignment(Pos.CENTER);
        }
    }

    private String castToString(Double item) {
        return String.valueOf(item);
    }
}
