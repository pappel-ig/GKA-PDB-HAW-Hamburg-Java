package de.haw_hamburg.gka.serializer;

import org.graphstream.graph.Graph;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class GrphStructureTest {

    @Test
    public void directedNodeTest() {
        Graph actual = new GrphStructure("graph", true, List.of(
                GrphLine.builder().node1("a").node2("b").build()
        )).toGraph();

        assertThat(actual.getId()).isEqualTo("graph");
        assertThat(actual.getAttribute("type")).isEqualTo("directed");
        assertThat(actual.getNode("a")).isNotNull();
        assertThat(actual.getNode("b")).isNotNull();
        assertTrue(actual.getNode("a").hasEdgeToward("b"));

    }

}