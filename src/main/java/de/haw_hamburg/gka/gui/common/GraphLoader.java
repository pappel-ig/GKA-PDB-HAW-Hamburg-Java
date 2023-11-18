package de.haw_hamburg.gka.gui.common;

import de.haw_hamburg.gka.gui.UIModal;
import de.haw_hamburg.gka.storage.GrphGraphStorage;
import de.haw_hamburg.gka.storage.GrphStructure;
import javafx.scene.layout.Pane;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Consumer;

public class GraphLoader {

    private final List<FxViewer> fxViewers = new ArrayList<>();
    private final Map<Class<?>, Graph> graphs = new HashMap<>();
    private final GrphGraphStorage store = new GrphGraphStorage();

    public void storeGraph(Class<?> context, File where) {
        if (graphs.containsKey(context)) {
            final Graph graph = graphs.get(context);
            if (Objects.nonNull(graph)) {
                final GrphStructure from = GrphStructure.from(graph);
                try {
                    store.storeTo(from, where);
                } catch (FileNotFoundException e) {
                    UIModal.showErrorDialog("Cant save to file");
                }
            }
        }
    }

    public void renderGraph(Pane pane, File file, Consumer<Node> forEachNode, Class<?> clazz) {
        if (Objects.isNull(file)) return;

        final Graph graph;
        try {
            graph = store.readFrom(file).toGraph();
        } catch (FileNotFoundException ex) {
            UIModal.showErrorDialog("Datei konnte nicht geladen werden!");
            return;
        }
        for (Node node : graph) {
            forEachNode.accept(node);
        }
        final FxViewer fxViewer = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        fxViewer.enableAutoLayout();
        pane.getChildren().clear();
        pane.getChildren().add((FxViewPanel) fxViewer.addDefaultView(false, new FxGraphRenderer()));
        fxViewers.add(fxViewer);
        graphs.put(clazz, graph);
    }

    public void reset() {
        for (FxViewer viewer : fxViewers) viewer.close();
    }
}
