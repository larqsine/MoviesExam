package gui.filterMovies;

import exceptions.MoviesException;
import gui.MainView.MainModel;
import gui.searchButton.ISearchGraphic;
import gui.searchButton.SearchGraphic;
import gui.searchButton.UndoGraphic;
import utility.ExceptionHandler;
import utility.GraphicIdValues;
import utility.InformationalMessages;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class FilterManager {
    private final MainModel model;
    private ISearchGraphic searchGraphic;
    private Label infoLabel;
    private Button searchButton;
    private TextField searchValue;

    public FilterManager(MainModel model,ISearchGraphic searchGraphic, Label infoLabel, Button searchButton, TextField searchValue) {
        this.model = model;
        this.infoLabel = infoLabel;
        this.searchButton = searchButton;
        this.searchValue = searchValue;
        this.searchGraphic=searchGraphic;
    }


    public void applyFilter(ActionEvent event) {
        String filter = searchValue.getText();
        if (searchButton.getGraphic().getId().equals(GraphicIdValues.SEARCH.getValue())) {
            if (!filter.isEmpty()) {
                try {
                    applySearchFilter(filter);
                } catch (MoviesException e) {
                    ExceptionHandler.displayInformationAlert(e.getMessage(),"Open a category");
                }
            } else {
                showInfoMessage(InformationalMessages.FILTER_EMPTY.getValue());
            }
        } else {
            clearSearchFilter();
        }
    }

    private void applySearchFilter(String filter) throws MoviesException {
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
