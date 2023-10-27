package de.haw_hamburg.gka.algo;

import lombok.Getter;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import java.util.Objects;

public class DjikstraPathResolver {

    private DjikstraAlgorithm djikstraAlgorithm;

    @Getter
    private PathStatus status;
    private Node startingNode;
    private Node endNode;

    public DjikstraPathResolver() {
        updateStatus(null);
    }

    public void startFrom(Node startNode) {
        this.djikstraAlgorithm = new DjikstraAlgorithm(startNode.getGraph());
        this.startingNode = startNode;
        djikstraAlgorithm.calculate(startNode);
        this.status = PathStatus.NO_TARGET;
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
        updateStatus(path);
        return path;
    }

    private void updateStatus(Path path) {
        if (Objects.isNull(startingNode)) status = PathStatus.NO_SOURCE;
        if (Objects.isNull(endNode)) status = PathStatus.NO_TARGET;
        if (path == null || path.getEdgeCount() == 0) this.status = PathStatus.TARGET_UNREACHABLE;
        else this.status = PathStatus.FOUND;
    }

    private boolean isNotStartOrEndNode(Node node) {
        return node.getAttribute("vorgänger", Node.class) != endNode
                && node != startingNode;
    }
}
