package de.haw_hamburg.gka.algo;

import lombok.RequiredArgsConstructor;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

@RequiredArgsConstructor
public class DjikstraAlgorithm {

    private Queue<Node> nodePriorityQueue;

    private void initialize(Node v_1) {
        v_1.setAttribute("abstand", 0);
        v_1.setAttribute("vorgänger", v_1);
        nodePriorityQueue = new PriorityQueue<>(Comparator.comparingInt(this::abstand));
        nodePriorityQueue.add(v_1);
        for (Node node : v_1.getGraph()) {
            if (!node.equals(v_1)) node.setAttribute("abstand", Integer.MAX_VALUE);
        }
    }

    private void calculate(final Node v_1) {
        initialize(v_1);
        while (!nodePriorityQueue.isEmpty()) {
            final Node u = nodePriorityQueue.poll();
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

    public Path getPathTo(Node startNode, Node endNode) {
        calculate(startNode);
        final Path path = new Path();
        Node current = endNode;
        while (current.hasAttribute("vorgänger") && isNotStartOrEndNode(current, startNode, endNode)) {
            Node next = current.getAttribute("vorgänger", Node.class);
            path.add(current, next.getEdgeBetween(current));
            current = next;
        }
        return path;
    }

    private boolean isNotStartOrEndNode(Node node, Node startNode, Node endNode) {
        return node.getAttribute("vorgänger", Node.class) != endNode
                && node != startNode;
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
