package de.haw_hamburg.gka.gui;

import de.haw_hamburg.gka.algo.DjikstraAlgorithm;
import de.haw_hamburg.gka.algo.DjikstraPathResolver;
import de.haw_hamburg.gka.gui.model.AbstractGraphController;
import de.haw_hamburg.gka.gui.model.GraphControlModel;
import de.haw_hamburg.gka.serializer.GrphGraphSerializer;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.Viewer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphVisualizationController extends AbstractGraphController {

    private final GrphGraphSerializer serializer = new GrphGraphSerializer();
    private final DjikstraPathResolver djikstraPathResolver = new DjikstraPathResolver();
    private Path path;
    public AnchorPane pane;

    @Override
    public void setModel(GraphControlModel model, Stage stage) {
        super.setModel(model, stage);
        model.getFile().addListener(this::newFileChosen);
        model.getSource().addListener(this::newSourceChosen);
        model.getTarget().addListener(this::newTargetChosen);
    }

    private void newSourceChosen(ObservableValue<? extends Node> observableValue, Node old, Node now) {
        resetPath();
        if (Objects.nonNull(old)) old.removeAttribute("ui.class");
        if (Objects.nonNull(now)) {
            now.setAttribute("ui.class", "start");
            djikstraPathResolver.startFrom(now);
        }
        if (Objects.nonNull(model.getTarget().get())) {
            path = djikstraPathResolver.getPathTo(model.getTarget().get());
            model.getLength().set(path.getEdgeCount());
        }
        renderPath();
    }

    private void newTargetChosen(ObservableValue<? extends Node> observableValue, Node old, Node now) {
        resetPath();
        if (Objects.nonNull(old)) old.removeAttribute("ui.class");
        if (Objects.nonNull(now)) {
            now.setAttribute("ui.class", "end");
            path = djikstraPathResolver.getPathTo(now);
            model.getLength().set(path.getEdgeCount());
        }
        renderPath();
    }

    private void renderPath() {
        if (Objects.nonNull(path)) {
            for (Edge edge : path.getEdgePath()) {
                edge.setAttribute("ui.class", "path");
            }
        }
    }

    private void resetPath() {
        if (Objects.nonNull(path)) {
            for (Edge edge : path.getEdgePath()) {
                edge.setAttribute("ui.class", "");
            }
        }
    }

    @SneakyThrows
    private void newFileChosen(ObservableValue<? extends File> observableValue, File old, File now) {
        model.getSource().set(null);
        model.getTarget().set(null);
        model.getLength().set(0);
        djikstraPathResolver.reset();
        final Graph loadedGraph = serializer.readFrom(now).toGraph();
        model.getNodes().clear();
        for (Node node : loadedGraph) {
            model.getNodes().add(node);
        }
        FxViewer fxViewer = new FxViewer(loadedGraph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        fxViewer.enableAutoLayout();
        pane.getChildren().clear();
        pane.getChildren().add((FxViewPanel) fxViewer.addDefaultView(false, new FxGraphRenderer()));
    }
}
