package utility;


import gui.MainView.MainModel;
import gui.components.player.PlayerCommander;
import gui.searchButton.ISearchGraphic;
import gui.searchButton.SearchGraphic;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaPlayer;


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

    public void bindDurationToModel(MainModel model, PlayerCommander pc) {
        pc.bindDurationToModel(model.currentTimeProperty());
        pc.bindTotalDurationToModel(model.totalTimeProperty());
    }

    public void initializeTimeSlider(Slider timeSlider, MainModel model) {
        timeSlider.setMin(0);
        timeSlider.maxProperty().bind(model.totalTimeProperty());
        timeSlider.valueProperty().bind(model.currentTimeProperty());
    }


    public void initializeVolumeSlider(Slider volumeSlider, MainModel mainModel) {
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(100);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mainModel.volumeLevelProperty().set((Double) newValue / 100);
        });
    }
}
