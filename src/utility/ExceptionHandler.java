package utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ExceptionHandler {


    public static void displayErrorAlert(Messages informationalMessages, String title) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        setTitleIfPresent(title, error);
        error.setContentText(informationalMessages.getValue());
        error.show();
    }


    public static void displayErrorAlert(String message, String title) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        setTitleIfPresent(title, error);
        error.setContentText(message);
        error.show();
    }


    public static void displayWarningAlert(Messages informationalMessages, String title) {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        setTitleIfPresent(title, warning);
        warning.setContentText(informationalMessages.getValue());
        warning.show();
    }

    public static void displayInformationAlert(Messages informationalMessages, String title) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        setTitleIfPresent(title, info);
        info.setContentText(informationalMessages.getValue());
        info.show();
    }

    public static void displayInformationAlert(String message, String title) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        setTitleIfPresent(title, info);
        info.setContentText(message);
        info.show();
    }

    public static void displayErrorAndCloseProgram(Stage currentStage, String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        setTitleIfPresent(title, alert);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                currentStage.close();
            }
        });
    }

    public  static void displayErrorAndWait(String message, String title) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        setTitleIfPresent(title, error);
        error.setContentText(message);
        error.showAndWait();
    }

    private static void setTitleIfPresent(String title, Alert error) {
        if (title != null) {
            error.setTitle(title);
        }

    }
}