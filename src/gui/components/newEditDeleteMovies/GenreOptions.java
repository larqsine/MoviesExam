package gui.components.newEditDeleteMovies;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import java.util.HashMap;
import java.util.Map;

class GenreOptions extends ListCell<String> {
    private final CheckBox checkBox;
    private static final Map<String, Boolean> checkedItems = new HashMap<>();

    public GenreOptions() {
        checkBox = new CheckBox();
        checkBox.setOnAction(e -> {
            String item = getItem();
            if (item != null) {
                checkedItems.put(item, checkBox.isSelected());
                System.out.println(item + " selection state: " + checkBox.isSelected());
            }
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            checkBox.setText(item);
            checkBox.setSelected(checkedItems.getOrDefault(item, false));
            setGraphic(checkBox);
        }
    }
}