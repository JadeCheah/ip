package bart;

import java.io.IOException;

import bart.controller.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Bartholomew using FXML.
 */
public class Main extends Application {

    private Bartholomew bartholomew = new Bartholomew("./data/bart.txt");

    @Override
    public void start(Stage stage) {
        try {
            setupUI(stage);
        } catch (IOException e) {
            System.err.println("Failed to load UI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupUI(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap);
        stage.setTitle("Bartholomew");
        stage.setScene(scene);
        stage.setMinHeight(220);
        stage.setMinWidth(417);
        fxmlLoader.<MainWindow>getController().setBart(bartholomew); // inject the Bart instance
        stage.show();
    }
}
