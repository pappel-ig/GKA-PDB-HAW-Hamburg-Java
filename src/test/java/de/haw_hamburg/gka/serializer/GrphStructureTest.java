package de.haw_hamburg.gka.serializer;

import org.graphstream.graph.Graph;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GrphStructureTest {

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
        assertThat(actual.getNode("a").getAttribute("#1")).isNotNull();
        assertThat(actual.getNode("c").getAttribute("#3")).isNotNull();
        assertThatStream(actual.getNode("b").attributeKeys()).contains("#oldB", "#newB");
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
        assertThat(actual.getNode("a").getEdgeToward("b").getAttribute("ui.label")).isEqualTo("a-b");
    }

}