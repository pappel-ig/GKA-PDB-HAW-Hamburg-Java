package de.haw_hamburg.gka.gui;

import de.haw_hamburg.gka.gui.model.AbstractGraphController;
import de.haw_hamburg.gka.gui.model.GraphControlModel;
import de.haw_hamburg.gka.serializer.GrphGraphSerializer;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;

import java.io.File;
import java.util.Objects;

public class GraphVisualizationController extends AbstractGraphController {

    private final GrphGraphSerializer serializer = new GrphGraphSerializer();
    public AnchorPane pane;

    @Override
    public void setModel(GraphControlModel model, Stage stage) {
        super.setModel(model, stage);
        model.getFile().addListener(this::newFileChosen);
        model.getSource().addListener(this::newSourceChosen);
        model.getTarget().addListener(this::newTargetChosen);
    }

    private void newSourceChosen(ObservableValue<? extends Node> observableValue, Node old, Node now) {
        if (Objects.nonNull(old)) old.removeAttribute("ui.class");
        if (Objects.nonNull(now)) now.setAttribute("ui.class", "start");
    }

    private void newTargetChosen(ObservableValue<? extends Node> observableValue, Node old, Node now) {
        if (Objects.nonNull(old)) old.removeAttribute("ui.class");
        if (Objects.nonNull(now)) now.setAttribute("ui.class", "end");
    }

    @SneakyThrows
    private void newFileChosen(ObservableValue<? extends File> observableValue, File old, File now) {
        final Graph loadedGraph = serializer.readFrom(now).toGraph();
        model.getNodes().clear();
        for (Node node : loadedGraph) {
            model.getNodes().add(node);
        }
        FxViewer fxViewer = new FxViewer(loadedGraph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        fxViewer.enableAutoLayout();
        pane.getChildren().clear();
        pane.getChildren().add((FxViewPanel) fxViewer.addDefaultView(false, new FxGraphRenderer()));
    }
}
