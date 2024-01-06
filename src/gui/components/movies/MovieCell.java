package gui.components.movies;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public abstract class MovieCell<T> extends TableCell<T,T> {
    private int cellWidth;
    private Duration deelayDuration= new Duration(0.2);
    private final Tooltip longDescription =  new Tooltip();
    public Label createSongCell(String item){
        Label label = new Label(item);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.LEFT);
        label.setPrefWidth(this.getCellWidth());
        label.setOnMouseEntered(event -> {
            longDescription.setText(item);
            longDescription.setShowDelay(this.getDeelayDuration());
            longDescription.getStyleClass().add("longDescription");
            Tooltip.install(this,longDescription);
        });
        label.setOnMouseExited(event->{
            Tooltip.uninstall(this,longDescription);
        });
        return label;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public void setDeelayDuration(Duration deelayDuration) {
        this.deelayDuration = deelayDuration;
    }

    public int getCellWidth() {
        return cellWidth;
    }
    public Duration getDeelayDuration() {
        return deelayDuration;
    }
}
