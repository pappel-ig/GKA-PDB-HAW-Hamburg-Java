package de.haw_hamburg.gka.algo;

import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

public class DjikstraPathResolver {

    private DjikstraAlgorithm djikstraAlgorithm;

    private Node startingNode;
    private Node endNode;

    public void startFrom(Node startNode) {
        this.djikstraAlgorithm = new DjikstraAlgorithm();
        this.startingNode = startNode;
        djikstraAlgorithm.calculate(startNode);
    }

    public Path getPathTo(Node endNode) {
        this.endNode = endNode;
        final Path path = new Path();
        Node current = endNode;
        while (current.hasAttribute("vorgänger") && isNotStartOrEndNode(current)) {
            Node next = current.getAttribute("vorgänger", Node.class);
            path.add(current, next.getEdgeBetween(current));
            current = next;
        }
        return path;
    }


    private boolean isNotStartOrEndNode(Node node) {
        return node.getAttribute("vorgänger", Node.class) != endNode
                && node != startingNode;
    }

    public void reset() {
        this.startingNode = null;
        this.endNode = null;
    }
}
