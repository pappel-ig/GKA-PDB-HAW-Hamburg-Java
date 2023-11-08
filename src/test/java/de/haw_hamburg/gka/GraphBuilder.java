package de.haw_hamburg.gka;

import lombok.RequiredArgsConstructor;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.Objects;
import java.util.UUID;

public class GraphBuilder {

    protected final Graph graph;
    private final boolean directed;

    public GraphBuilder(String name, boolean directed) {
        this.graph = new MultiGraph(name);
        this.directed = directed;
    }

    public GraphLineBuilder node1(String node1) {
        return new GraphLineBuilder(this, graph, directed, node1);
    }

    public Graph graph() {
        return graph;
    }

    @RequiredArgsConstructor
    public static class GraphLineBuilder {
        private final GraphBuilder graphLinesBuilder;
        private final Graph graph;
        private final boolean directed;
        private final String node1;
        private String attr1;
        private String node2 = "b";
        private String attr2;
        private String edge;
        private int weight = 1;

        public GraphLineBuilder attr1(String attr1) {
            this.attr1 = attr1;
            return this;
        }

        public GraphLineBuilder node2(String id) {
            node2 = id;
            return this;
        }

        public GraphLineBuilder attr2(String attr2) {
            this.attr2 = attr2;
            return this;
        }

        public GraphLineBuilder edge(String edge) {
            this.edge = edge;
            return this;
        }

        public GraphLineBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public GraphBuilder done() {
            final Node node1 = graph.addNode(this.node1);
            if (Objects.nonNull(this.attr1)) node1.setAttribute("attribute", this.attr1);
            node1.setAttribute("ui.label", node1.getId());
            if (Objects.nonNull(this.node2)) {
                final Node node2 = graph.addNode(this.node2);
                final Edge edge = graph.addEdge(UUID.randomUUID().toString(), node1, node2, directed);
                edge.setAttribute("weight", Math.max(this.weight, 1));
                if (Objects.nonNull(this.attr2)) node2.setAttribute("attribute", this.attr2);
                if (Objects.nonNull(this.edge)) edge.setAttribute("edge", this.edge);
                edge.setAttribute("ui.label", String.valueOf(Math.max(this.weight, 1)));
                node2.setAttribute("ui.label", node2.getId());
            }
            return graphLinesBuilder;
        }
    }

}
