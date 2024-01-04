package gui.components.category;
import be.Category;
import gui.components.listeners.CategorySelection;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class TitleCell extends ListCell<Category> {
    private final Tooltip longDescription =  new Tooltip();
    private int cellWidth ;
    private Duration deelayDuration = new Duration(0.2);
    @Override
    protected void updateItem(Category item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(createCell(item));
        }
    }
    public TitleCell(int width, Duration duration){
        this.cellWidth=width;
        if(duration!=null){
            this.deelayDuration=duration;
        }
    }


    private Label createCell(be.Category item){
        Label label = new Label(item.getName());
        label.setPrefWidth(cellWidth);
        longDescription.setText(item.getName());
        longDescription.setShowDelay(this.deelayDuration);
         setOnMouseEnterAction(label);
       setOnMouseExitAction(label);
       return label;

    }

    private void setOnMouseEnterAction(Label label){
        label.setOnMouseEntered(event->
                Tooltip.install(this,longDescription));
    }

    private void setOnMouseExitAction(Label label)
    {
        label.setOnMouseExited(event -> Tooltip.uninstall(this,longDescription));
    }

    public void setOnMouseClick(CategorySelection listener){
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (!this.isEmpty() && event.getClickCount() == 2) {
                listener.categorySelectionHandler(getItem().getId());
            }
        });

    };
    }




