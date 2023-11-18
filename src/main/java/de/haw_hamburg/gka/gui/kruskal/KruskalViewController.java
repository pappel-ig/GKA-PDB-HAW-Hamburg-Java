package de.haw_hamburg.gka.gui.kruskal;

import de.haw_hamburg.gka.gui.AbstractController;
import de.haw_hamburg.gka.gui.common.GraphFileChooser;
import de.haw_hamburg.gka.gui.common.GraphLoader;
import de.haw_hamburg.gka.gui.dijkstra.DijkstraModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class KruskalViewController extends AbstractController<KruskalModel> {
    @FXML public AnchorPane KruskalViewPane;
    @FXML public AnchorPane kruskalControls;
    @FXML public AnchorPane kruskalVisualization;
    @FXML public KruskalControlsController kruskalControlsController;
    @FXML public KruskalVisualizationController kruskalVisualizationController;

    @Override
    public void setModel(KruskalModel model, Stage stage, GraphLoader graphLoader, GraphFileChooser graphFileChooser) {
        super.setModel(model, stage, graphLoader, graphFileChooser);
        kruskalControlsController.setModel(model, stage, graphLoader, graphFileChooser);
        kruskalVisualizationController.setModel(model, stage, graphLoader, graphFileChooser);
    }
}
