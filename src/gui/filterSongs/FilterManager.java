package gui.filterSongs;

import gui.MainView.MainModel;
import gui.searchButton.IsearchGraphic;
import gui.searchButton.SearchGraphic;
import gui.searchButton.UndoGraphic;
import utility.InformationalMessages;

import java.awt.*;
import java.awt.event.ActionEvent;

public class FilterManager {
    private final MainModel model;
    private IsearchGraphic searchGraphic;
    private Label infoLabel;
    private Button SearchButton;
    private TextField searchValue;

    public FilterManager(MainModel model) {
        this.model = model;
        this.searchGraphic = searchGraphic;
        this.infoLabel = infoLabel;
        this. searchButton = searchButton;
        this. searchValue = searchValue;
    }


    public void applyFilter(ActionEvent event) {
        String filter = searchValue.getText();
        if (searchButton.getGraphic().getId().equals(GraphicIdValues.SEARCH.getValue())) {
            if (!filter.isEmpty()) {
                applySearchFilter(filter);
            } else {
                showInfoMessage(InformationalMessages.FILTER_EMPTY.getValue());
            }
        }
        else {
            clearSearchFilter();
        }
    }

    private void applySearchFilter(String filter) {
        searchGraphic = new UndoGraphic();
        model.applyFilter(filter);
        infoLabel.setVisible(false);
        searchButton.setGraphic(searchGraphic.getGraphic());
        searchValue.setText("");
        searchValue.setEditable(false);
    }

    private void clearSearchFilter() {
        searchGraphic = new SearchGraphic();
        searchButton.setGraphic(searchGraphic.getGraphic());
        searchValue.setEditable(true);
        model.resetFilter();
    }

    private void showInfoMessage(String message) {
        infoLabel.setVisible(true);
    }
}
