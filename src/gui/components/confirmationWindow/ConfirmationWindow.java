package gui.components.confirmationWindow;

import gui.components.listeners.ConfirmationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.layout.VBox;
import utility.ExceptionHandler;
import utility.InformationalMessages;
public class ConfirmationWindow {
    private ConfirmationController confirmationController;
    @FXML
    private VBox container;
    @FXML
    private Label operationTitle;
    @FXML
    private Label operationInformation;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;


    public ConfirmationWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmationWindow.fxml"));
        loader.setController(this);
        try {
            container = loader.load();
        } catch (IOException e) {
            ExceptionHandler.displayErrorAlert(InformationalMessages.FXML_MISSING,null);
        }
        if (container != null) {
            initializeHandlers();
        }
    }

    public VBox getConfirmationWindow() {
        return container;
    }


    public void setOperationTitle(String title) {
        this.operationTitle.setText(title);
    }

    public void setOperationInformation(String title) {
        this.operationInformation.setText(title);
        operationInformation.setStyle("-fx-text-fill: white");
    }


    private void initializeHandlers() {
        addConfirmHandler();
        addCancelHandler();
    }

    private void addConfirmHandler() {
        this.confirmButton.setOnMouseClicked(event ->
                {
                    confirmationController.confirmationEventHandler(true);
                    getCurrentStage(event).close();
                }
        );
    }

    private void addCancelHandler() {
        this.cancelButton.setOnMouseClicked(event -> {
            confirmationController.confirmationEventHandler(false);
            getCurrentStage(event).close();
        });
    }

    public void setConfirmationController(ConfirmationController confirmationController) {
        this.confirmationController = confirmationController;
    }

    private Stage getCurrentStage(MouseEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}
