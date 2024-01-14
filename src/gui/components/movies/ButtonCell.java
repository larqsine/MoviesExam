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
        button.setOnAction(event -> {
            Movie movie = getTableView().getItems().get(getIndex());
            model.setPlayMovie(true);
            model.setCurrentPlayingMovie(movie.getId());
            this.playerCommander.processOperation(Operations.PLAY_CURRENT);
            model.setPlayButtonValue(PlayButtonGraphic.STOP.getValue());
            model.setPlayButtonFromTableId(button.getId());
            movieTable.refresh();
        });

        hoverListener = (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
//                change button to pause is the button id equals with the urrent button;
                if (button.getId().equals(model.getPlayButtonFromTableId())) {
                    button.setGraphic(pauseSvg());
                    setGraphic(button);
                } else {
                    button.setGraphic(playSvg());
                    setGraphic(button);

                }

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

    private void updateButtonGraphic(boolean state, String id, boolean isHovered) {
        if (state && id.equals(model.getPlayButtonFromTableId()) && isHovered) {
            button.setGraphic(pauseSvg());
        } else if (isHovered) {
            button.setGraphic(playSvg());
        } else {
            setGraphic(null);
        }
    }

    private SVGPath playSvg() {
        SVGPath svgPath = new SVGPath();
        svgPath.setContent("M0 0 L0 10 L10 5 z");
        svgPath.setFill(Color.BLACK);
        return svgPath;
    }

    private SVGPath pauseSvg() {
        SVGPath pauseIcon = new SVGPath();
        pauseIcon.setContent("M0 0h3v10H0zM5 0h3v10H5z");
        pauseIcon.setFill(Color.BLACK);
        return pauseIcon;
    }

}
