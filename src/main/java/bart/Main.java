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
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBart(bartholomew); // inject the Bart instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
