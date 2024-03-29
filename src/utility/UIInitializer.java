package utility;

import be.Movie;

import gui.MainView.MainModel;
import gui.components.player.PlayerCommander;
import gui.searchButton.ISearchGraphic;
import gui.searchButton.SearchGraphic;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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

    public void initializeDurationLabels(Label elapsedTime, Label totalTime, PlayerCommander pc) {
        pc.bindMediaTimeToScreen(elapsedTime);
        pc.bindTotalTimeToScreen(totalTime);
    }
    public void initializeTotalDurationModel(MainModel model,PlayerCommander pc){
        pc.bindTotalDurationToModel(model.totalTimeProperty());
    }

    public void initializeVolumeSlider(Slider volumeSlider, MainModel mainModel) {
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(100);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mainModel.volumeLevelProperty().set((Double) newValue / 100);
        });
    }

    public void initializeTimeSlider(Slider timeSlider, MainModel model) {
        timeSlider.setMin(0);
        timeSlider.maxProperty().bind(model.totalTimeProperty());
    }



    /**bind the button disablePropriety to the  model observable list*/
    public void addMovieOpenedListener(ObservableList<Movie> movies,Button button) {
        BooleanBinding isMoviesEmpty = Bindings.createBooleanBinding(
                () -> movies.size() <= 1,
                movies
        );
        button.disableProperty().bind(isMoviesEmpty);
    }

}
