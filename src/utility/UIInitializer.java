package utility;


import gui.MainView.MainModel;
import gui.components.player.PlayerCommander;
import gui.searchButton.ISearchGraphic;
import gui.searchButton.SearchGraphic;
import javafx.beans.property.DoubleProperty;
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

    public void initializeDurationLabels(Label elapsedTime, Label totalTime, PlayerCommander pc){
        pc.bindMediaTimeToScreen(elapsedTime);
        pc.bindTotalTimeToScreen(totalTime);
    }

//    public void initializeDurationSlider(Slider slider, DoubleProperty duration){
//        slider.valueProperty().bind();
//    }
    public void bindDurationToModel(MainModel model,PlayerCommander pc){
        pc.bindDurationToModel(model.currentTimeProperty());
        pc.bindTotalDurationToModel(model.totalTimeProperty());
    }

    public void initializeTimeSlider(Slider timeSlider, MainModel model) {
        timeSlider.setMin(0);
        timeSlider.maxProperty().bind(model.totalTimeProperty());
        timeSlider.valueProperty().bind(model.currentTimeProperty());
    }
}
