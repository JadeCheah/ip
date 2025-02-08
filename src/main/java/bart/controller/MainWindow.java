package bart.controller;

import bart.Bartholomew;
import bart.command.Command;
import bart.command.CommandResult;
import bart.util.Parser;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 * Manages user input, displays responses, and controls interactions between the GUI and {@code Bartholomew}.
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
     * Initializes the GUI components.
     * Binds the scroll pane to automatically scroll to the bottom when new dialog boxes are added.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the instance of Bartholomew to be used for handling user commands.
     *
     * @param b The Bartholomew instance to be injected.
     */
    public void setBart(Bartholomew b) {
        bartholomew = b;
    }


    /**
     * Handles user input when the send button is pressed or the Enter key is hit.
     * - Displays the user's input in the dialog box.
     * - Processes the input using {@code Bartholomew} and generates a response.
     * - Displays Bartholomew's response in the dialog box.
     * - If the user enters "bye", the application terminates.
     */
    @FXML
    private void handleUserInput() {
        String fullCommand = userInput.getText();
        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(fullCommand, userImage)
        );
        Command command = Parser.parseCommand(fullCommand);
        CommandResult result = bartholomew.getResponse(command);

        dialogContainer.getChildren().add(
                DialogBox.getBartDialog(result.getMessage(), bartImage)
        );
        // Check if the command is an exit command
        if (result.isExit()) {
            // Delay program exit to allow UI update
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> Platform.exit()); // Properly close JavaFX application
            delay.play();
        }

        userInput.clear();
    }
}
