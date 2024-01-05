package utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ExceptionHandler {
    public ExceptionHandler() {
    }

    public void displayErrorAlert(Messages informationalMessages, String title) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        setTitleIfPresent(title, error);
        error.setContentText(informationalMessages.getValue());
        error.show();
    }


    public void displayErrorAlert(String message, String title) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        setTitleIfPresent(title, error);
        error.setContentText(message);
        error.show();
    }


    public void displayWarningAlert(Messages informationalMessages, String title) {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        setTitleIfPresent(title, warning);
        warning.setContentText(informationalMessages.getValue());
        warning.show();
    }

    public void displayInformationAlert(Messages informationalMessages, String title) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        setTitleIfPresent(title, info);
        info.setContentText(informationalMessages.getValue());
        info.show();
    }

    public void displayInformationAlert(String message, String title) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        setTitleIfPresent(title, info);
        info.setContentText(message);
        info.show();
    }

    public void displayErrorAndCloseProgram(Stage currentStage, String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        setTitleIfPresent(title, alert);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                currentStage.close();
            }
        });
    }

    public void displayErrorAndWait(String message, String title) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        setTitleIfPresent(title, error);
        error.setContentText(message);
        error.showAndWait();
    }

    private void setTitleIfPresent(String title, Alert error) {
        if (title != null) {
            error.setTitle(title);
        }

    }
}