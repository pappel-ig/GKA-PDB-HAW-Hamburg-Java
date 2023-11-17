package de.haw_hamburg.gka.gui;

import de.haw_hamburg.gka.gui.dijkstra.DijkstraViewController;
import de.haw_hamburg.gka.gui.kruskal.KruskalModel;
import de.haw_hamburg.gka.gui.kruskal.KruskalViewController;
import de.haw_hamburg.gka.gui.dijkstra.DijkstraModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainViewController {

    @FXML AnchorPane dijkstraView;
    @FXML AnchorPane kruskalView;
    @FXML
    DijkstraViewController dijkstraViewController;
    @FXML KruskalViewController kruskalViewController;

    public void initialize(Stage stage) {
        final DijkstraModel dijkstraModel = new DijkstraModel();
        final KruskalModel kruskalModel = new KruskalModel();
        dijkstraViewController.setModel(dijkstraModel, stage);
        kruskalViewController.setModel(kruskalModel, stage);
    }

    public void shutdown() {
        dijkstraViewController.reset();
    }
}
