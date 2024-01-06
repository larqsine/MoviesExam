package gui.components.movies;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class TableCell<T> extends javafx.scene.control.TableCell<T,String> {
    private int cellWidth;
    private Duration deelayDuration= new Duration(0.2);
    private final Tooltip longDescription =  new Tooltip();


    public TableCell(int width, Duration duration){
        this.cellWidth=width;
        if(duration!=null){
            this.deelayDuration=duration;
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
    private Label createSongCell(String item){
        Label label = new Label(item);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.LEFT);
        label.setPrefWidth(this.getCellWidth());
        label.getStyleClass().add("songLabel");
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

    public int getCellWidth() {
        return cellWidth;
    }
    public Duration getDeelayDuration() {
        return deelayDuration;
    }


}
