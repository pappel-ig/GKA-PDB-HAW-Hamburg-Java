package de.haw_hamburg.gka.gui;

import de.haw_hamburg.gka.algo.DijkstraAlgorithm;
import de.haw_hamburg.gka.gui.model.AbstractGraphController;
import de.haw_hamburg.gka.gui.model.GraphControlModel;
import de.haw_hamburg.gka.storage.GrphGraphStorage;
import de.haw_hamburg.gka.storage.GrphStructure;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class GraphVisualizationController extends AbstractGraphController {

    private final GrphGraphStorage store = new GrphGraphStorage();
    private final DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
    private Path path;
    public AnchorPane pane;
    private Graph loadedGraph;
    @Override
    public void setModel(GraphControlModel model, Stage stage) {
        super.setModel(model, stage);
        model.getFile().addListener(this::newFileChosen);
        model.getSource().addListener(this::newSourceChosen);
        model.getTarget().addListener(this::newTargetChosen);
        model.getSaveTo().addListener(this::newSaveTo);
    }

    private void newSourceChosen(ObservableValue<? extends Node> observableValue, Node old, Node now) {
        resetPath();
        if (Objects.nonNull(old)) old.removeAttribute("ui.class");
        if (Objects.nonNull(now)) now.setAttribute("ui.class", "start");
        if (Objects.nonNull(model.getTarget().get())) {
            path = algorithm.getPathTo(now, model.getTarget().get());
            model.getLength().set(path.getEdgeCount());
        }
        renderPath();
    }

    private void newTargetChosen(ObservableValue<? extends Node> observableValue, Node old, Node now) {
        resetPath();
        if (Objects.nonNull(old)) old.removeAttribute("ui.class");
        if (Objects.nonNull(now)) {
            now.setAttribute("ui.class", "end");
            if (Objects.nonNull(model.getSource().get())) {
                path = algorithm.getPathTo(model.getSource().get(), now);
                model.getLength().set(path.getEdgeCount());
            }
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

    private void newFileChosen(ObservableValue<? extends File> observableValue, File old, File now) {
        if (Objects.isNull(now)) return;

        try {
            loadedGraph = store.readFrom(now).toGraph();
        } catch (FileNotFoundException ex) {
            UIModal.showErrorDialog("Datei konnte nicht geladen werden!");
            return;
        }
        for (Node node : loadedGraph) {
            model.getNodes().add(node);
        }
        FxViewer fxViewer = new FxViewer(loadedGraph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        fxViewer.enableAutoLayout();
        pane.getChildren().clear();
        pane.getChildren().add((FxViewPanel) fxViewer.addDefaultView(false, new FxGraphRenderer()));
    }

    private void newSaveTo(ObservableValue<? extends File> observableValue, File old, File now) {
        final GrphStructure from = GrphStructure.from(loadedGraph);
        try {
            store.storeTo(from, now);
        } catch (FileNotFoundException e) {
            UIModal.showErrorDialog("Cant save to file");
        }
    }
}
