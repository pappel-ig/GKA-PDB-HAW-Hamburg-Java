package de.haw_hamburg.gka.serializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.List;
import java.util.Objects;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GrphStructure {
    private String name;
    private boolean directed;
    private List<GrphLine> grphLines;

    public Graph toGraph() {
        final Graph graph = new MultiGraph(name);
        for (GrphLine line : grphLines) {
            if (Objects.isNull(graph.getNode(line.getNode1()))) graph.addNode(line.getNode1());
            if (Objects.isNull(graph.getNode(line.getNode2()))) graph.addNode(line.getNode2());

        }
        return graph;
    }

    public static GrphStructure from(Graph graph) {
        return null;
    }
}