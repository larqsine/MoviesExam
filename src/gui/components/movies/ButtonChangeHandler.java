//package gui.components.movies;
//
//import be.Movie;
//import gui.MainView.MainModel;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//
//
//public class ButtonChangeHandler {
//
//    private MainModel model;
//    private TableView<Movie> movieTable;
//
//    public ButtonChangeHandler(MainModel model, TableView<Movie> movieTable) {
//        this.model = model;
//        this.movieTable = movieTable;
//    }
//
//
//    public void changeButtonGraphic() {
//        for (TableColumn<Movie, ?> column : movieTable.getColumns()) {
//            if (column == movieTable.getColumns().get(0)) {
//                TableCell<Movie, ?> cell = column.getCellFactory().call(column).call(movieTable);
//                if (cell != null && cell.getGraphic() instanceof Button button) {
//                    if (button.getUserData().equals(model.getPlayButtonFromTableId())) {
//
//                    }
//                }
//            }
//        }
//    }
//
//}
