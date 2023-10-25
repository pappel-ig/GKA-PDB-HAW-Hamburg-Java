package de.haw_hamburg.gka.serializer;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

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
        assertThat(serializer.readFrom(getFile("grph/directedNode.grph"))).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void undirectedNodes() {
        GrphStructure expectedStructure = new GrphStructure("graph", false, List.of(
                GrphLine.builder().node1("a").node2("b").build(),
                GrphLine.builder().node1("b").node2("c").build(),
                GrphLine.builder().node1("c").node2("a").build()
        ));
        assertThat(serializer.readFrom(getFile("grph/undirectedNode.grph"))).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void weightedNodes() {
        GrphStructure expectedStructure = new GrphStructure("graph", true, List.of(
                GrphLine.builder().node1("Hamburg").node2("Greifswald").weight(372).build(),
                GrphLine.builder().node1("Greifswald").node2("Zehdenick").weight(210).build(),
                GrphLine.builder().node1("Zehdenick").node2("Hamburg").weight(414).build()
        ));
        assertThat(serializer.readFrom(getFile("grph/weightedNodes.grph"))).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void spacedNodeNames() {
        GrphStructure expectedStructure = new GrphStructure("graph", true, List.of(
                GrphLine.builder().node1("Hamburg").node2("Greifswald").weight(372).build(),
                GrphLine.builder().node1("Greifswald").node2("Zehdenick").weight(210).build(),
                GrphLine.builder().node1("Zehdenick").node2("Hamburg").weight(414).build()
        ));
        assertThat(serializer.readFrom(getFile("grph/spacedNodeNames.grph"))).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void nodesWithAttributes() {
        GrphStructure expectedStructure = new GrphStructure("graph", true, List.of(
                GrphLine.builder().node1("a").attr1("1").node2("b").attr2("2").build()
        ));
        assertThat(serializer.readFrom(getFile("grph/nodesWithAttributes.grph"))).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void edgeWithLabel() {
        GrphStructure expectedStructure = new GrphStructure("graph", true, List.of(
                GrphLine.builder().node1("a").node2("b").edge("a zu b").build(),
                GrphLine.builder().node1("b").node2("c").edge("b zu c").build(),
                GrphLine.builder().node1("c").node2("a").edge("c zu a").build()
        ));
        assertThat(serializer.readFrom(getFile("grph/edgeWithLabel.grph"))).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void sanitizedInput() {
        GrphStructure expectedStructure = new GrphStructure("graph", true, List.of(
                GrphLine.builder().node1("Hamburg").node2("Greifswald").weight(372).build(),
                GrphLine.builder().node1("Greifswald").attr1("MK").node2("Zehdenick").weight(210).build(),
                GrphLine.builder().node1("Zehdenick").node2("Hamburg").edge("HH").weight(414).build()
        ));
        assertThat(serializer.readFrom(getFile("grph/sanitizedInput.grph"))).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void errorUndefinedType() {
        assertThrows(IllegalArgumentException.class, () -> {
            serializer.readFrom(getFile("grph/errorUndefinedType.grph"));
        });
    }

    @Test
    public void errorFormatInBody() {
        assertThrows(IllegalArgumentException.class, () -> {
            serializer.readFrom(getFile("grph/errorFormatInBody.grph"));
        });
    }

    @SneakyThrows
    public File getFile(String name) {
        return new File(this.getClass().getClassLoader().getResource(name).toURI());
    }
}