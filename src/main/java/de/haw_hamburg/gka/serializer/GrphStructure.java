package de.haw_hamburg.gka.serializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GrphStructure {
    private String name;
    private boolean directed;
    private List<GrphLine> grphLines;

    public Graph toGraph() {
        final Graph graph = new MultiGraph(name, false, false);
        for (GrphLine line : grphLines) {
            // create node or use existing
            final Node node1 = graph.addNode(line.getNode1());
            final Node node2 = graph.addNode(line.getNode2());
            // create directed/undirected edge between node1 and node2
            final Edge edge = graph.addEdge(UUID.randomUUID().toString(), node1, node2, directed);
            // set weight
            if (line.getWeight() < 1) edge.setAttribute("weight", 1);
            else edge.setAttribute("weight", line.getWeight());
            // set attributes
            if (Objects.nonNull(line.getAttr1())) node1.setAttribute(String.format("#%s", line.getAttr1()));
            if (Objects.nonNull(line.getAttr2())) node2.setAttribute(String.format("#%s", line.getAttr2()));
            // set edge and node labels
            if (Objects.nonNull(line.getEdge())) edge.setAttribute("ui.label", line.getEdge());
            node1.setAttribute("ui.label", node1.getId());
            node2.setAttribute("ui.label", node2.getId());
        }
        return graph;
    }

    public static GrphStructure from(Graph graph) {
        return null;
    }
}