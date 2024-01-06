package utility;


import gui.searchButton.ISearchGraphic;
import gui.searchButton.SearchGraphic;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class UIInitializer {
    public void initializeSearchView(ISearchGraphic searchGraphic, Button searchButton, TextField searchValue, Label infoLabel) {
        searchGraphic = new SearchGraphic();
        searchButton.setGraphic(searchGraphic.getGraphic());
        searchValue.textProperty().addListener((obs, oldValue, newValue) -> {
            if (infoLabel.isVisible()) {
                infoLabel.setVisible(false);
            }
        });
    }
}
