package de.haw_hamburg.gka.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.graphstream.graph.Edge;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrphLine {
    private String node1;
    private String attr1;
    private String node2;
    private String attr2;
    private String edge;
    private int weight;

    public static GrphLine from(Edge edge) {
        return GrphLine.builder()
                .node1(edge.getNode0().getId())
                .attr1(edge.getNode0().getAttribute("attribute", String.class))
                .node2(edge.getNode1().getId())
                .attr2(edge.getNode1().getAttribute("attribute", String.class))
                .weight(edge.hasAttribute("weight") ? edge.getAttribute("weight", Integer.class) : 1)
                .edge(edge.getAttribute("label", String.class))
                .build();
    }
}