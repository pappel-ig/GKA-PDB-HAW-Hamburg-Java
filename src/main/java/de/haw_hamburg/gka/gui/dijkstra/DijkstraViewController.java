package de.haw_hamburg.gka.gui.dijkstra;

import de.haw_hamburg.gka.gui.AbstractController;
import de.haw_hamburg.gka.gui.common.GraphFileChooser;
import de.haw_hamburg.gka.gui.common.GraphLoader;
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
    public void setModel(DijkstraModel model, Stage stage, GraphLoader graphLoader, GraphFileChooser graphFileChooser) {
        super.setModel(model, stage, graphLoader, graphFileChooser);
        graphControlsController.setModel(model, stage, graphLoader, graphFileChooser);
        graphVisualizationController.setModel(model, stage, graphLoader, graphFileChooser);
    }

    @Override
    public void reset() {
        graphVisualizationController.reset();
        graphControlsController.reset();
    }
}
