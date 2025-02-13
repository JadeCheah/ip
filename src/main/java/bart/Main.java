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
            assert ap != null : "AnchorPane should be loaded";

            Scene scene = new Scene(ap);
            stage.setScene(scene);

            MainWindow controller = fxmlLoader.getController();
            assert controller != null : "MainWindow controller should be initialized";

            controller.setBart(bartholomew);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
