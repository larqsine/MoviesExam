package gui.components.movies;
import be.Movie;
import gui.MainView.MainModel;
import gui.components.player.PlayerCommander;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import utility.GraphicIdValues;
import utility.Operations;
import utility.PlayButtonGraphic;


public class ButtonCell extends TableCell<Movie, String> {
    private final Button button;
    private MainModel model;
    private PlayerCommander playerCommander;
    private ChangeListener<Boolean> hoverListener;


    public ButtonCell(int width, int height, MainModel model, PlayerCommander playerCommander, TableView<Movie> movieTable) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.model = model;
        this.playerCommander = playerCommander;
        button = new Button();
        button.setGraphic(playSvg());
        button.setUserData("");
        button.setOnAction(event -> {
            Movie movie = getTableView().getItems().get(getIndex());
            model.setCurrentPlayingMovie(movie.getId());
            if (model.getPlayButtonState()) {
                this.playerCommander.processOperation(Operations.PAUSE);
                model.setPlayButtonValue(PlayButtonGraphic.PLAY.getValue());
                model.setPlayButtonState(false);
                model.setPlayButtonFromTableId(button.getId());
                movieTable.refresh();
            } else {
                model.setPlayMovie(true);
                playOperation(button.getUserData().toString());
                model.setPlayButtonValue(PlayButtonGraphic.STOP.getValue());
                model.setPlayButtonFromTableId(button.getId());
                model.setPlayButtonState(true);
                button.setUserData(Boolean.FALSE);
                movieTable.refresh();
            }
        });
        hoverListener = (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
//                change button to pause if the button id equals with the current button;
                if (button.getId().equals(model.getPlayButtonFromTableId())) {
                    button.setGraphic(model.getPlayButtonState() ? pauseSvg() : playSvg());
                } else {
                    button.setGraphic(playSvg());
                }
                setGraphic(button);
            } else {
                setGraphic(null);
            }
        };
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        TableRow<Movie> row = getTableRow();
        if (row != null) {
            row.hoverProperty().removeListener(hoverListener);
            if (empty) {
                setGraphic(null);
            } else {
                String id = row.getItem().getId() + row.getItem().getName() + model.getCurrentOpenedCategory();
                button.setId(id);
                row.hoverProperty().addListener(hoverListener);
            }
        }
    }

    private SVGPath playSvg() {
        SVGPath playIcon = new SVGPath();
        playIcon.setContent("M0 0 L0 10 L10 5 z");
        playIcon.setFill(Color.BLACK);
        playIcon.setId(GraphicIdValues.PLAY.getValue());
        return playIcon;

    }

    private SVGPath pauseSvg() {
        SVGPath pauseIcon = new SVGPath();
        pauseIcon.setContent("M0 0h3v10H0zM5 0h3v10H5z");
        pauseIcon.setFill(Color.BLACK);
        pauseIcon.setId(GraphicIdValues.PAUSE.getValue());
        return pauseIcon;
    }

    private void playOperation(String operation){
        switch(operation){
            case "play":this.playerCommander.processOperation(Operations.PLAY);
            break;
            default:this.playerCommander.processOperation(Operations.PLAY_CURRENT);
        }
    }


}
