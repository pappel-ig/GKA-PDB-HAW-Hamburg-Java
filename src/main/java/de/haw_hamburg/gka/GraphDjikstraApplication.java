package de.haw_hamburg.gka;

import de.haw_hamburg.gka.gui.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class GraphDjikstraApplication extends Application {

    @SneakyThrows
    public static void main(String[] args) {
        launch();
//        System.setProperty("org.graphstream.ui", "javafx");
//        GrphGraphSerializer serializer = new GrphGraphSerializer();
//        Graph graph = serializer.readFrom(ResourceLoadHelper.loadFile("graph01.grph")).toGraph();
//        graph.display();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/gui/mainView.fxml"));
        primaryStage.setScene(new Scene(myLoader.load()));
        final MainViewController controller = myLoader.getController();
        controller.initialize(primaryStage);
        primaryStage.show();
    }
}