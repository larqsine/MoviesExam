
import gui.MainView.MainViewController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
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
            initializeStage(primaryStage, root, mvC);
        } else {
            ExceptionHandler.displayErrorAndWait(mvC.getExceptionMessage(), InformationalMessages.INITIALIZING_ERROR.getValue());
        }

    }

    private static void initializeStage(Stage primaryStage, Parent root, MainViewController controller) {
        primaryStage.showingProperty().addListener(((observable, oldValue, newValue) -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                if (newValue) {
                    controller.initializeOldListView(primaryStage);
                }
            });
            pause.play();
        }
        ));


        Scene scene = new Scene(root);
        primaryStage.setTitle("Movies app");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}