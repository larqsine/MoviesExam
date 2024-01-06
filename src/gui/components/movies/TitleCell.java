package gui.components.movies;
import javafx.util.Duration;

public class TitleCell<T> extends MovieCell<String> {



    public TitleCell(int width, Duration duration){
        this.setCellWidth(width);
        if (duration != null) {
            this.setDeelayDuration(duration);
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setOnMouseEntered(null);
            setOnMouseExited(null);
        } else {
            setGraphic(createSongCell(item));
        }
    }
}
