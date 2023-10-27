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
        model.getStatus().set(djikstraPathResolver.getStatus());
    }

    private void newSourceChosen(ObservableValue<? extends Node> observableValue, Node old, Node now) {
        if (Objects.nonNull(path)) {
            for (Edge edge : path.getEdgePath()) {
                edge.removeAttribute("ui.class");
            }
        }
        if (Objects.nonNull(old)) old.removeAttribute("ui.class");
        if (Objects.nonNull(now)) now.setAttribute("ui.class", "start");
        djikstraPathResolver.startFrom(now);
        model.getStatus().set(djikstraPathResolver.getStatus());
    }

    private void newTargetChosen(ObservableValue<? extends Node> observableValue, Node old, Node now) {
        if (Objects.nonNull(path)) {
            for (Edge edge : path.getEdgePath()) {
                edge.setAttribute("ui.class", "");
            }
        }
        if (Objects.nonNull(old)) old.removeAttribute("ui.class");
        if (Objects.nonNull(now)) now.setAttribute("ui.class", "end");
        path = djikstraPathResolver.getPathTo(now);
        for (Edge edge : path.getEdgePath()) {
            edge.setAttribute("ui.class", "path");
        }
        model.getStatus().set(djikstraPathResolver.getStatus());
        model.getLength().set(path.getEdgeCount());
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
        model.getStatus().set(djikstraPathResolver.getStatus());
    }
}
