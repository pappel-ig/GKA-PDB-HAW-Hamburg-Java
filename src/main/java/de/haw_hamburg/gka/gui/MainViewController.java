package de.haw_hamburg.gka.gui;

import de.haw_hamburg.gka.gui.dijkstra.DijkstraViewControllerDijkstra;
import de.haw_hamburg.gka.gui.kruskal.KruskalViewController;
import de.haw_hamburg.gka.gui.dijkstra.model.DijkstraGraphControlModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainViewController {

    @FXML AnchorPane dijkstraView;
    @FXML AnchorPane kruskalView;
    @FXML DijkstraViewControllerDijkstra dijkstraViewController;
    @FXML KruskalViewController kruskalViewController;

    public void initialize(Stage stage) {
        DijkstraGraphControlModel model = new DijkstraGraphControlModel();
        dijkstraViewController.setModel(model, stage);
    }
}
