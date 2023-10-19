package de.haw_hamburg.gka.aufg2;

import de.haw_hamburg.gka.GraphHelper;
import lombok.Data;
import org.graphstream.algorithm.util.DisjointSets;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public class KruskalAlgorithm {

    private final Graph graph;

    public MsfResult msf() {
        final List<Edge> edges = graph.edges()
                .sorted(Comparator.comparingInt(GraphHelper::weight))
                .toList();

        final DisjointSets<Node> disjointSet = new DisjointSets<>();
        final List<Edge> msfEdgeList = new ArrayList<>();
        int weight = 0;
        for (Edge minEdge : edges) {
            final Node sourceNode = minEdge.getSourceNode();
            final Node targetNode = minEdge.getTargetNode();
            disjointSet.add(sourceNode);
            disjointSet.add(targetNode);
            if (disjointSet.union(sourceNode, targetNode)) {
                weight += GraphHelper.weight(minEdge);
                msfEdgeList.add(minEdge);
            }
        }
        return new MsfResult(weight, msfEdgeList);
    }
}
