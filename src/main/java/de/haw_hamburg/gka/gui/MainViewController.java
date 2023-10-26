package de.haw_hamburg.gka.gui;

import de.haw_hamburg.gka.gui.model.GraphControlModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainViewController {
    @FXML public AnchorPane graphControls;
    @FXML public AnchorPane graphVisualization;

    @FXML public GraphControlsController graphControlsController;

    @FXML public GraphVisualizationController graphVisualizationController;


    public void initialize(Stage stage) {
        GraphControlModel model = new GraphControlModel();
        graphControlsController.setModel(model, stage);
        graphVisualizationController.setModel(model, stage);
    }
}
