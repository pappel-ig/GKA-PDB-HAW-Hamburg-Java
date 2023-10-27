package de.haw_hamburg.gka.algo;

import lombok.RequiredArgsConstructor;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

@RequiredArgsConstructor
public class DjikstraAlgorithm {

    private final Graph graph;
    private PriorityQueue<Node> nodePriorityQueue;

    private void initialize(Node v_1) {
        v_1.setAttribute("abstand", 0);
        v_1.setAttribute("vorgänger", v_1);
        nodePriorityQueue = new PriorityQueue<>(Comparator.comparingInt(this::abstand));
        for (Node node : graph) {
            if (!node.equals(v_1)) node.setAttribute("abstand", Integer.MAX_VALUE);
//            nodePriorityQueue.add(node);
        }
        nodePriorityQueue.add(v_1);
    }

    public void calculate(final Node v_1) {
        initialize(v_1);
        while (!nodePriorityQueue.isEmpty()) {
            Node u = nodePriorityQueue.poll();
            u.neighborNodes().filter(u::hasEdgeToward).filter(node -> !node.equals(v_1)).forEach(v -> {
                final int distanceBetweenNode = getDistanceBetweenNode(u, v);
                if (distanceBetweenNode < abstand(v)) {
                    v.setAttribute("abstand", distanceBetweenNode);
                    v.setAttribute("vorgänger", u);
                    nodePriorityQueue.remove(v);
                    nodePriorityQueue.add(v);
                }
            });
        }
    }

    private int getDistanceBetweenNode(Node u, Node v) {
        return abstand(u) + weight(u.getEdgeBetween(v));
    }

    private int abstand(Node node) {
        return node.getAttribute("abstand", Integer.class);
    }

    private int weight(Edge edge) {
        return edge.getAttribute("weight", Integer.class);
    }
}
