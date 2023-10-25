package de.haw_hamburg.gka.serializer;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GrphGraphSerializerTest {

    GrphGraphSerializer serializer = new GrphGraphSerializer();

    @Test
    @SneakyThrows
    public void directedNodes() {
        GrphStructure expectedStructure = new GrphStructure("graph", true, List.of(
                GrphLine.builder().node1("a").node2("b").build(),
                GrphLine.builder().node1("b").node2("c").build(),
                GrphLine.builder().node1("c").node2("a").build()
        ));
        assertThat(serializer.readFrom(getFile("directedNode.grph"))).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void undirectedNodes() {
        GrphStructure expectedStructure = new GrphStructure("graph", false, List.of(
                GrphLine.builder().node1("a").node2("b").build(),
                GrphLine.builder().node1("b").node2("c").build(),
                GrphLine.builder().node1("c").node2("a").build()
        ));
        assertThat(serializer.readFrom(getFile("undirectedNode.grph"))).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void weightedNodes() {
        GrphStructure expectedStructure = new GrphStructure("graph", true, List.of(
                GrphLine.builder().node1("Hamburg").node2("Greifswald").weight(372).build(),
                GrphLine.builder().node1("Greifswald").node2("Zehdenick").weight(210).build(),
                GrphLine.builder().node1("Zehdenick").node2("Hamburg").weight(414).build()
        ));
        assertThat(serializer.readFrom(getFile("weightedNodes.grph"))).isEqualTo(expectedStructure);
    }

    @SneakyThrows
    public File getFile(String name) {
        return new File(this.getClass().getClassLoader().getResource(name).toURI());
    }
}