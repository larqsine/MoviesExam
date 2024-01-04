package gui.components.category;
import be.Category;
import gui.components.listeners.CategorySelection;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class CategoryView extends ListView<Category> {
    public CategoryView(CategorySelection selectionListener,ObservableList<Category> items) {
        this.setItems(items);
        this.setCellFactory(cell -> {
            TitleCell titleCell = new TitleCell(100, null);
            titleCell.setOnMouseClick(selectionListener);
            return titleCell;
        });
    }

    public void setCategories(ObservableList<Category> songs) {
        this.setItems(songs);
    }

    public void setHeight(int height) {
        this.setPrefHeight(height);
    }

    public void setWidth(int width) {

        this.setPrefWidth(width);
    }

}
