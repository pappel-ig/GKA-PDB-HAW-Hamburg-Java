package de.haw_hamburg.gka.serializer;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrphStructureTest {

    @Nested
    class ToGraph {
        @Test
        public void directedNodeTest() {
            Graph actual = new GrphStructure("graph", true, List.of(
                    GrphLine.builder().node1("a").node2("b").build()
            )).toGraph();

            assertThat(actual.getId()).isEqualTo("graph");
            assertThat(actual.getNode("a")).isNotNull();
            assertThat(actual.getNode("b")).isNotNull();
            assertTrue(actual.getNode("a").hasEdgeToward("b"));
            assertTrue(actual.getNode("a").getEdgeToward("b").isDirected());
        }

        @Test
        public void undirectedNodeTest() {
            Graph actual = new GrphStructure("graph", false, List.of(
                    GrphLine.builder().node1("a").node2("b").build()
            )).toGraph();

            assertThat(actual.getId()).isEqualTo("graph");
            assertThat(actual.getNode("a")).isNotNull();
            assertThat(actual.getNode("b")).isNotNull();
            assertTrue(actual.getNode("a").hasEdgeToward("b"));
            assertFalse(actual.getNode("a").getEdgeToward("b").isDirected());
        }

        @Test
        public void weightedNodes() {
            Graph actual = new GrphStructure("graph", false, List.of(
                    GrphLine.builder().node1("a").node2("b").weight(5).build()
            )).toGraph();

            assertThat(actual.getId()).isEqualTo("graph");
            assertThat(actual.getNode("a")).isNotNull();
            assertThat(actual.getNode("b")).isNotNull();
            assertTrue(actual.getNode("a").hasEdgeToward("b"));
            assertThat(actual.getNode("a").getEdgeToward("b").getAttribute("weight", Integer.class)).isEqualTo(5);
        }

        @Test
        public void nodesAttributes() {
            Graph actual = new GrphStructure("graph", false, List.of(
                    GrphLine.builder().node1("a").attr1("1").node2("b").attr2("oldB").build(),
                    GrphLine.builder().node1("b").attr1("newB").node2("c").attr2("3").build()
            )).toGraph();
            assertThat(actual.getId()).isEqualTo("graph");
            assertThat(actual.getNode("a")).isNotNull();
            assertThat(actual.getNode("b")).isNotNull();
            assertThat(actual.getNode("c")).isNotNull();
            assertTrue(actual.getNode("a").hasEdgeToward("b"));
            assertTrue(actual.getNode("b").hasEdgeToward("c"));
            assertThat(actual.getNode("b").getAttribute("attribute")).isNotNull();
            assertThat(actual.getNode("c").getAttribute("attribute")).isNotNull();
            assertThat(actual.getNode("b").getAttribute("attribute", String.class)).isEqualTo("newB");
            assertThat(actual.getNode("c").getAttribute("attribute", String.class)).isEqualTo("3");
        }

        @Test
        public void labelsSetOnNodesAndEdges() {
            Graph actual = new GrphStructure("graph", false, List.of(
                    GrphLine.builder().node1("a").node2("b").edge("a-b").build()
            )).toGraph();

            assertThat(actual.getId()).isEqualTo("graph");
            assertThat(actual.getNode("a")).isNotNull();
            assertThat(actual.getNode("a").getAttribute("ui.label")).isEqualTo("a");
            assertThat(actual.getNode("b")).isNotNull();
            assertThat(actual.getNode("b").getAttribute("ui.label")).isEqualTo("b");
            assertTrue(actual.getNode("a").hasEdgeToward("b"));
            assertThat(actual.getNode("a").getEdgeToward("b").getAttribute("ui.label")).isEqualTo("1");
        }
    }

    @Nested
    class FromGraph {
        @Test
        public void directedNodeTest() {
            GrphStructure expected = new GrphStructure("graph", true, List.of(
                    GrphLine.builder().node1("a").node2("b").weight(1).build()
            ));

            Graph graph = new MultiGraph("graph");
            graph.setAutoCreate(true);
            graph.setStrict(false);
            graph.addEdge("edge", "a", "b", true);
            assertThat(GrphStructure.from(graph)).isEqualTo(expected);
        }

        @Test
        public void weightedNodeTest() {
            GrphStructure expected = new GrphStructure("graph", false, List.of(
                    GrphLine.builder().node1("a").node2("b").weight(5).build()
            ));

            Graph graph = new MultiGraph("graph");
            graph.setAutoCreate(true);
            graph.setStrict(false);
            Edge edge = graph.addEdge("edge", "a", "b", false);
            edge.setAttribute("weight", 5);
            assertThat(GrphStructure.from(graph)).isEqualTo(expected);
        }

        @Test
        public void nodesAttributes() {
            GrphStructure expected = new GrphStructure("graph", true, List.of(
                    GrphLine.builder().node1("a").attr1("atr1").node2("b").attr2("atr2").weight(1).build()
            ));

            Graph graph = new MultiGraph("graph");
            graph.setAutoCreate(true);
            graph.setStrict(false);
            graph.addEdge("edge", "a", "b", true);
            graph.getNode("a").setAttribute("attribute", "atr1");
            graph.getNode("b").setAttribute("attribute", "atr2");
            assertThat(GrphStructure.from(graph)).isEqualTo(expected);
        }

        @Test
        public void labelsOnNodes() {
            GrphStructure expected = new GrphStructure("graph", true, List.of(
                    GrphLine.builder().node1("a").node2("b").weight(1).edge("edge").build()
            ));
            Graph graph = new MultiGraph("graph");
            graph.setAutoCreate(true);
            graph.setStrict(false);
            Edge edge = graph.addEdge("edge", "a", "b", true);
            edge.setAttribute("ui.label", "edge");
        }
    }

}