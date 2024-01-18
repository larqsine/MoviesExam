package gui.components.movies;

import be.Movie;
import exceptions.MoviesException;
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
import utility.ExceptionHandler;
import utility.Operations;



public class ButtonCell extends TableCell<Movie, String> {
    private static final String PLAY_ICON_PATH = "M0 0 L0 10 L10 5 z";
    private final Button button;
    private MainModel model;
    private ChangeListener<Boolean> hoverListener;

    public ButtonCell(int width, int height, MainModel model, PlayerCommander playerCommander, TableView<Movie> movieTable) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.model = model;
        button = new Button();
        button.setGraphic(playSvg());
        button.setOnAction(event -> {
            Movie movie = getTableView().getItems().get(getIndex());
            model.setCurrentPlayingMovie(movie.getId());
            try {
                model.verifyViewDate(movie.getId());
            } catch (MoviesException e) {
                ExceptionHandler.displayErrorAlert(e.getMessage(),"Date can not be updated");
            }
            model.setObservablePlayPropertyValue(true);
            model.setPlayButtonFromTableId(button.getId());
            playerCommander.processOperation(Operations.PLAY_CURRENT);
            movieTable.refresh();
        });

        hoverListener = (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
                if (button.getId().equals(model.getPlayButtonFromTableId())) {
                    button.setDisable(true);
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
        playIcon.setContent(PLAY_ICON_PATH);
        playIcon.setFill(Color.BLACK);
        return playIcon;
    }

}
