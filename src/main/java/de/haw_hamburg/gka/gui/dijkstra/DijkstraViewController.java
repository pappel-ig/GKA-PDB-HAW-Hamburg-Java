package de.haw_hamburg.gka.gui.dijkstra;

import de.haw_hamburg.gka.gui.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DijkstraViewController extends AbstractController<DijkstraModel> {

    @FXML public AnchorPane dijkstraView;
    @FXML public AnchorPane graphControls;
    @FXML public AnchorPane graphVisualization;

    @FXML public DijkstraControlsController graphControlsController;
    @FXML public DijkstraVisualizationController graphVisualizationController;

    @Override
    public void setModel(DijkstraModel model, Stage stage) {
        super.setModel(model, stage);
        graphControlsController.setModel(model, stage);
        graphVisualizationController.setModel(model, stage);
    }

    @Override
    public void reset() {
        graphVisualizationController.reset();
        graphControlsController.reset();
    }
}
