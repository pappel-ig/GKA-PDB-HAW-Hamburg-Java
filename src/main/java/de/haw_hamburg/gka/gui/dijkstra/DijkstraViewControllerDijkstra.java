package de.haw_hamburg.gka.gui.dijkstra;

import de.haw_hamburg.gka.gui.dijkstra.model.DijkstraAbstractGraphController;
import de.haw_hamburg.gka.gui.dijkstra.model.DijkstraGraphControlModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DijkstraViewControllerDijkstra extends DijkstraAbstractGraphController {

    @FXML public AnchorPane dijkstraView;
    @FXML public AnchorPane graphControls;
    @FXML public AnchorPane graphVisualization;

    @FXML public GraphControlsControllerDijkstra graphControlsController;
    @FXML public GraphVisualizationControllerDijkstra graphVisualizationController;

    @Override
    public void setModel(DijkstraGraphControlModel model, Stage stage) {
        super.setModel(model, stage);
        graphControlsController.setModel(model, stage);
        graphVisualizationController.setModel(model, stage);
    }
}
