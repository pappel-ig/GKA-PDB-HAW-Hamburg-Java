package de.haw_hamburg.gka.gui;

import de.haw_hamburg.gka.gui.model.AbstractGraphController;
import de.haw_hamburg.gka.gui.model.GraphControlModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.graphstream.graph.Node;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

public class GraphControlsController extends AbstractGraphController {
    public Label length;
    public ChoiceBox<Node> target;
    public ChoiceBox<Node> source;
    public Button loadFile;
    public Button saveFile;

    @Override
    public void setModel(GraphControlModel model, Stage stage) {
        super.setModel(model, stage);
        model.getNodes().addListener(this::newNodes);
        model.getLength().addListener(this::lengthChanged);
        source.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> model.getSource().setValue(newValue));
        target.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> model.getTarget().setValue(newValue));
    }

    private void lengthChanged(ObservableValue<? extends Number> observableValue, Number old, Number now) {
        length.setText(now.toString());
    }

    private void newNodes(ListChangeListener.Change<? extends Node> change) {
        source.setItems(model.getNodes());
        target.setItems(model.getNodes());
    }

    public void openFile() {
        model.reset();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Graph auswählen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Graph (.grph)", "*.grph"));
        final File chosen = fileChooser.showOpenDialog(stage);
        if (Objects.nonNull(chosen)) {
            model.getFile().setValue(chosen);
        } else {
            UIModal.showInfoDialog("Es wurde keine Datei selektiert -> keine Datei geladen");
        }
    }

    @SneakyThrows
    public void saveFile() {
        if (Objects.nonNull(model.getFile().get())) {
            File file = model.getFile().get();
            Path parent = file.toPath().getParent();
            File newFile = parent.resolve(file.getName().replaceAll(".grph", "") + "_formatted.grph").toFile();
            UIModal.showInfoDialog(String.format("Die Datei wurde unter '%s' gespeichert!", newFile.getPath()));
            model.getSaveTo().set(newFile);
        } else {
            UIModal.showErrorDialog("Es wurde keine Datei geladen!");
        }
    }
}
