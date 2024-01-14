package gui.components.movies;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class TitleCell<T> extends MovieCell<String> {



    public TitleCell(int width,int height, Duration duration){
      super(width,height,duration);
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
            setGraphic(createMovieCell(item));
            setAlignment(Pos.CENTER);
        }
    }
}
