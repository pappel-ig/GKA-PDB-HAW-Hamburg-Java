package de.haw_hamburg.gka.algo;

import lombok.RequiredArgsConstructor;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import java.util.*;

@RequiredArgsConstructor
public class DijkstraAlgorithm {

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
        Node current = endNode;
        Stack<Map.Entry<Node, Edge>> entries = new Stack<>();
        while (current.hasAttribute("vorgänger") && isNotStartOrEndNode(current, startNode, endNode)) {
            Node next = current.getAttribute("vorgänger", Node.class);
            entries.push(Map.entry(current, next.getEdgeBetween(current)));
            current = next;
        }
        final Path path = new Path();
        while (!entries.isEmpty()) {
            Map.Entry<Node, Edge> entry = entries.pop();
            if (path.empty()) path.add(entry.getValue().getNode0(), entry.getValue());
            else path.add(entry.getValue());
        }
        return path;
    }

    private boolean isNotStartOrEndNode(Node node, Node startNode, Node endNode) {
        return node.getAttribute("vorgänger", Node.class) != endNode
                && node != startNode;
    }

    private int getDistanceBetweenNode(Node u, Node v) {
        return abstand(u) + weight(getMinEdgeBetween(u, v));
    }

    private Edge getMinEdgeBetween(Node u, Node v) {
        return u.leavingEdges()
                .filter(edge -> edge.getNode0().equals(v) || edge.getNode1().equals(v))
                .min(Comparator.comparingInt(this::weight))
                .orElseThrow();
    }

    private int abstand(Node node) {
        return node.getAttribute("abstand", Integer.class);
    }

    private int weight(Element edge) {
        return edge.getAttribute("weight", Integer.class);
    }
}
