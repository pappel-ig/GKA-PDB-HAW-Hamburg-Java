package de.haw_hamburg.gka.algo;

import lombok.RequiredArgsConstructor;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

@RequiredArgsConstructor
public class DjikstraAlgorithm {

    private final Graph graph;
    private final Node startNode;
    private final Node endNode;

    private void initialize() {
        for (Node node : graph) {
            node.setAttribute("abstand", Integer.MAX_VALUE);
        }
        startNode.setAttribute("abstand", 0);
    }

//    private void distanzAktualisieren(Node node, Edge edge, )

}
