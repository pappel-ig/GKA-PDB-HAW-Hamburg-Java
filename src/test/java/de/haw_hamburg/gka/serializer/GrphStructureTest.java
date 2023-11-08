package de.haw_hamburg.gka.serializer;

import de.haw_hamburg.gka.GraphBuilder;
import de.haw_hamburg.gka.GrphBuilder;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrphStructureTest {

    @Nested
    class ToGraph {
        @Test
        public void directedNodeTest() {
            GrphStructure grphStructure = GrphBuilder.builder("graph", true)
                    .node1("a").node2("b").next().grph();
            Graph actual = grphStructure.toGraph();

            assertThat(actual.getId()).isEqualTo("graph");
            assertThat(actual.getNode("a")).isNotNull();
            assertThat(actual.getNode("b")).isNotNull();
            assertTrue(actual.getNode("a").hasEdgeToward("b"));
            assertTrue(actual.getNode("a").getEdgeToward("b").isDirected());
        }

        @Test
        public void undirectedNodeTest() {
            GrphStructure grphStructure = GrphBuilder.builder("graph", false)
                    .node1("a").node2("b").next().grph();
            Graph actual = grphStructure.toGraph();

            assertThat(actual.getId()).isEqualTo("graph");
            assertThat(actual.getNode("a")).isNotNull();
            assertThat(actual.getNode("b")).isNotNull();
            assertTrue(actual.getNode("a").hasEdgeToward("b"));
            assertFalse(actual.getNode("a").getEdgeToward("b").isDirected());
        }

        @Test
        public void weightedNodes() {
            GrphStructure grphStructure = GrphBuilder.builder("graph", false)
                    .node1("a").node2("b").weight(5).next().grph();
            Graph actual = grphStructure.toGraph();

            assertThat(actual.getId()).isEqualTo("graph");
            assertThat(actual.getNode("a")).isNotNull();
            assertThat(actual.getNode("b")).isNotNull();
            assertTrue(actual.getNode("a").hasEdgeToward("b"));
            assertThat(actual.getNode("a").getEdgeToward("b").getAttribute("weight", Integer.class)).isEqualTo(5);
        }

        @Test
        public void nodesAttributes() {
            GrphStructure grphStructure = GrphBuilder.builder("graph", false)
                    .node1("a").attr1("1").node2("b").attr2("oldB").next()
                    .node1("b").attr1("newB").node2("c").attr2("3").next()
                    .grph();
            Graph actual = grphStructure.toGraph();

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
            GrphStructure grphStructure = GrphBuilder.builder("graph", false)
                    .node1("a").node2("b").edge("a-b").next().grph();
            Graph actual = grphStructure.toGraph();

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
            GrphStructure expected = GrphBuilder.builder("graph", true)
                    .node1("a").node2("b").next().grph();

            GrphStructure actual = GrphStructure.from(GraphBuilder.builder("graph", true)
                    .node1("a").node2("b").done().graph());

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        public void weightedNodeTest() {
            GrphStructure expected = GrphBuilder.builder("graph", true)
                    .node1("a").node2("b").weight(5).next().grph();

            GrphStructure actual = GrphStructure.from(GraphBuilder.builder("graph", true)
                    .node1("a").node2("b").weight(5).done().graph());

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        public void nodesAttributes() {
            GrphStructure expected = GrphBuilder.builder("graph", true)
                    .node1("a").attr1("atr1").node2("b").attr2("atr2").next().grph();

            GrphStructure actual = GrphStructure.from(GraphBuilder.builder("graph", true)
                    .node1("a").attr1("atr1").node2("b").attr2("atr2").done().graph());

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        public void labelsOnNodes() {
            GrphStructure expected = GrphBuilder.builder("graph", true)
                    .node1("a").node2("b").edge("a-b").next().grph();

            GrphStructure actual = GrphStructure.from(GraphBuilder.builder("graph", true)
                    .node1("a").node2("b").edge("a-b").done().graph());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class WriteToMappingTest {
        // TODO
    }

}