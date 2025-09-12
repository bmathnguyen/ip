package bill;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class MainWindow extends VBox {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bill bill;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image billImage = new Image(this.getClass().getResourceAsStream("/images/bill.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        // Add initial welcome message
        dialogContainer.getChildren().add(DialogBox.getDukeDialog("Hello! I'm Bill. What can I do for you?", billImage));
    }

    public void setBill(Bill b) {
        bill = b;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bill.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, billImage)
        );
        userInput.clear();
        if (input.equalsIgnoreCase("bye")) {
            // Use this alternative method to disable the controls
            userInput.disableProperty().set(true);
            sendButton.disableProperty().set(true);
        }
    }
}