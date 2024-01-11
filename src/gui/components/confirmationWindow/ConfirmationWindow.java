package gui.components.confirmationWindow;

import gui.components.listeners.ConfirmationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmationWindow {

    private ConfirmationController confirmationController;

    @FXML
    private Label operationTitle;

    @FXML
    private Label operationInformation;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmationWindow.fxml"));
            loader.setController(this);

            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            handleFxmlLoadingError();
        }
    }

    public void setConfirmationController(ConfirmationController confirmationController) {
        this.confirmationController = confirmationController;
    }

    public void setOperationTitle(String title) {
        this.operationTitle.setText(title);
    }

    public void setOperationInformation(String information) {
        this.operationInformation.setText(information);
    }

    private void handleFxmlLoadingError() {
    }

    @FXML
    private void handleConfirmation() {
        confirmationController.confirmationEventHandler(true);
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        confirmationController.confirmationEventHandler(false);
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public HBox getConfirmationWindow() {
        return null;
    }
}
