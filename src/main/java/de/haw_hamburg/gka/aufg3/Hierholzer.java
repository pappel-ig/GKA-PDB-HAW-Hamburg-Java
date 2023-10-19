package de.haw_hamburg.gka.aufg3;

import lombok.RequiredArgsConstructor;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;


@RequiredArgsConstructor
public class Hierholzer {
    public List<Node> euler(Graph graph) {
        if (graph.getEdgeCount() < 1) return new ArrayList<>();

        final List<Node> result = new ArrayList<>(graph.getEdgeCount());
        final Stack<Node> work = new Stack<>();
        final List<Edge> deleted = new ArrayList<>(graph.getEdgeCount());

        work.push(graph.getNode(0));

        while (!work.isEmpty()) {
            final Node currentNode = work.peek();
            final Optional<Edge> edgeOptional = currentNode.edges().findAny();
            // Kreise durchführen bis nicht mehr möglich (keine Kanten mehr verfügbar)
            if (edgeOptional.isPresent()) {
                final Edge edge = edgeOptional.get();
                work.push(edge.getOpposite(currentNode));
                deleted.add(graph.removeEdge(edge));
            // wenn keine Kante mehr vorhanden ist -> Kreis geschlossen -> Stack leeren und Kreis in das Result hinzufügen.
            } else {
                result.add(currentNode);
                work.pop();
            }
        }
        // Graph Restore (Gelöschte Kanten wieder hinzufügen)
        for (Edge edge : deleted) {
            graph.addEdge(edge.getId(), edge.getSourceNode(), edge.getTargetNode());
        }
        return result;
    }
}
