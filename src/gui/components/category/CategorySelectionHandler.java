package gui.components.category;
import gui.components.listeners.CategorySelection;

public class CategorySelectionHandler  implements CategorySelection {
    @Override
    public void categorySelectionHandler(int id) {
        System.out.println("test selection");
    }
}
