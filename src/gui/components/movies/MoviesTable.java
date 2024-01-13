package gui.components.movies;

import be.Genre;
import be.Movie;
import gui.components.listeners.MovieSelectionListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.Date;
import java.util.List;

public class MoviesTable extends TableView<Movie> {
    private MovieSelectionListener movieSelectionListener;

    private final int defaultHeight = 513;

    public MoviesTable(MovieSelectionListener listener,ObservableList<Movie> movies) {
        this.setItems(movies);
        this.movieSelectionListener = listener;
        setupColumns();
        setRowFactory();
        this.setHeight(defaultHeight);
    }

    private void setupColumns() {

        TableColumn<Movie, String> titleColumn = new TableColumn<>("Name");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        titleColumn.setPrefWidth(95);
        titleColumn.setCellFactory(column -> new TitleCell(95, null));

        TableColumn<Movie, Double> imdbRating = new TableColumn<>("IMDB Rating");
        imdbRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        imdbRating.setPrefWidth(50);
        imdbRating.setCellFactory(column ->
                new DoubleCell(50, null));


        TableColumn<Movie, Date> lastView = new TableColumn<>("LastView");
        lastView.setCellValueFactory(new PropertyValueFactory<>("lastView"));
        lastView.setPrefWidth(48);
        lastView.setCellFactory(column -> new DateCell(48, null));


        TableColumn<Movie, Double> personalRating = new TableColumn<>("Personal Rating");
        personalRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));
        personalRating.setPrefWidth(48);
        personalRating.setCellFactory(column ->  new DoubleCell(48, null));

        TableColumn<Movie, List<Genre>> categories = new TableColumn<>("Genre");
        categories.setCellValueFactory(new PropertyValueFactory<>("genres"));
        categories.setPrefWidth(95);
        categories.setCellFactory(column -> new ListCell(95, null));

        this.getColumns().addAll(titleColumn, imdbRating, lastView, personalRating, categories);
        this.setPlaceholder(new Label("No movies to display"));
    }

    private void setRowFactory() {
        this.setRowFactory(tv -> {
            TableRow<Movie> row = new TableRow<>();
            row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    movieSelectionListener.playSelectedMovie(row.getItem().getId(), true);
                } else if (!row.isEmpty() && event.getClickCount() == 1) {

                }
            });
            return row;
        });
    }

    public void setMovies(ObservableList<Movie> movies) {
        this.setItems(movies);
    }

    public void setMovieSelectionListener(MovieSelectionListener movieSelectionListener) {
        this.movieSelectionListener = movieSelectionListener;
    }


}


