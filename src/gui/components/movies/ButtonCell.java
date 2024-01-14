package gui.components.movies;
import be.Movie;
import gui.MainView.MainModel;
import gui.components.player.PlayerCommander;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import utility.Operations;
import utility.PlayButtonGraphic;

public class ButtonCell extends TableCell<Movie,String> {
    private final Button button;
    private final SVGPath svgPath;
    private MainModel model;
    private PlayerCommander playerCommander;
    public ButtonCell(int width,int height,MainModel model,PlayerCommander playerCommander) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.model=model;
        this.playerCommander=playerCommander;
        svgPath = new SVGPath();
        svgPath.setContent("M0 0 L0 10 L10 5 z");
        svgPath.setFill(Color.BLACK);
        button = new Button();
        button.setGraphic(svgPath);
        button.setOnAction(event -> {
            Movie movie = getTableView().getItems().get(getIndex());
            model.setPlayMovie(true);
            model.setCurrentPlayingMovie(movie.getId());
            this.playerCommander.processOperation(Operations.PLAY_CURRENT);
            model.setPlayButtonValue(PlayButtonGraphic.STOP.getValue());
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(null);
            TableRow<Movie> row = getTableRow();
            if (row != null) {
                row.hoverProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        setGraphic(button);
                    } else {
                        setGraphic(null);
                    }
                });
            }
        }
    }

}
