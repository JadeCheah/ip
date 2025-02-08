package bart.controller;

import bart.Bartholomew;
import bart.command.Command;
import bart.util.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bartholomew bartholomew;

    private Image bartImage = new Image(this.getClass().getResourceAsStream("/images/Bartholomew.jpg"));
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));

    /**
     * Initializes the dialog container to automatically scroll to the bottom whenever new content
     * is added.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setBart(Bartholomew b) {
        bartholomew = b;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String fullCommand = userInput.getText();
        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(fullCommand, userImage)
        );
        Command command = Parser.parseCommand(fullCommand);
        String response = bartholomew.getResponse(command);
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(response, bartImage)
        );
        userInput.clear();
    }
}
