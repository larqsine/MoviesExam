
import gui.MainView.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.ExceptionHandler;
import utility.InformationalMessages;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/MainView/MainView.fxml"));
        Parent root = loader.load();
        MainViewController mvC = loader.getController();
        boolean initializingError = mvC.isError();
        if (!initializingError) {
            initializeStage(primaryStage, root);
        } else {
            initializeStage(primaryStage, root);
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            exceptionHandler.displayErrorAndWait(mvC.getExceptionMessage(), InformationalMessages.INITIALIZING_ERROR.getValue());
            primaryStage.close();
        }

    }

    private static void initializeStage(Stage primaryStage, Parent root) {
        Scene scene = new Scene(root);
        primaryStage.setTitle("Movies app");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}