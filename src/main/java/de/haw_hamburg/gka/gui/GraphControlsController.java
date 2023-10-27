package de.haw_hamburg.gka.gui;

import de.haw_hamburg.gka.gui.model.AbstractGraphController;
import de.haw_hamburg.gka.gui.model.GraphControlModel;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.graphstream.graph.Node;

import java.io.File;
import java.util.Objects;

public class GraphControlsController extends AbstractGraphController {
    public Label length;
    public Label status;
    public Button start;
    public ChoiceBox<Node> target;
    public ChoiceBox<Node> source;
    public Button loadFile;

    @Override
    public void setModel(GraphControlModel model, Stage stage) {
        super.setModel(model, stage);
        model.getNodes().addListener(this::newNodes);
        source.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> model.getSource().setValue(newValue));
        target.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> model.getTarget().setValue(newValue));
    }

    private void newNodes(ListChangeListener.Change<? extends Node> change) {
        source.setItems(model.getNodes());
        target.setItems(model.getNodes());
    }

    public void openFile() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Graph auswählen");
        final File chosen = fileChooser.showOpenDialog(stage);
        if (Objects.nonNull(chosen)) {
            model.getFile().setValue(chosen);
        }
    }
}
