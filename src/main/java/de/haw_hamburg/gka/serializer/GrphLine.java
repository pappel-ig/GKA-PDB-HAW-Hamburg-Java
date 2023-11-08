package de.haw_hamburg.gka.serializer;

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

    @Override
    public String toString() {
        String node1 = this.node1;
        String attr1 = Objects.nonNull(this.attr1) ? String.format(":%s", this.attr1) : "";
        String node2 = Objects.nonNull(this.node2) ? String.format("-%s", this.node2) : "";
        String attr2 = Objects.nonNull(this.attr2) ? String.format(":%s", this.attr2) : "";
        String edge = Objects.nonNull(this.edge) ? String.format(" (%s)", this.edge) : "";
        String weight = this.weight != 1 ? String.format("::%d", this.weight) : "";
        return String.format("%s%s%s%s%s%s;", node1, attr1, node2, attr2, edge, weight);
    }
}