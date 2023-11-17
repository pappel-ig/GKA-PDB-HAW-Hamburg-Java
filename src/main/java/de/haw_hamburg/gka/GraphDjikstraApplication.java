package de.haw_hamburg.gka;

import de.haw_hamburg.gka.gui.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class GraphDjikstraApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @SneakyThrows
    @Override
    public void start(Stage primaryStage) {
        final FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/gui/mainView.fxml"));
        primaryStage.setScene(new Scene(myLoader.load()));
        primaryStage.setTitle("GKA Graph Explorer");
        final MainViewController controller = myLoader.getController();
        controller.initialize(primaryStage);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            controller.shutdown();
            Platform.exit();
        });
    }
}
