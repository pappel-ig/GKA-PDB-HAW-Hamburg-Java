package de.haw_hamburg.gka.algo.aufg1.storage;

import de.haw_hamburg.gka.GrphBuilder;
import de.haw_hamburg.gka.aufg1.storage.GrphReader;
import de.haw_hamburg.gka.aufg1.storage.GrphStructure;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static de.haw_hamburg.gka.TestHelper.readFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GrphReaderTest {



    @Test
    @SneakyThrows
    public void directedNodes() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/directedNode.grph")));
        GrphStructure expectedStructure = GrphBuilder.builder("graph", true)
                .node1("a").node2("b").weight(0).next()
                .node1("b").node2("c").weight(0).next()
                .node1("c").node2("a").weight(0).next()
                .grph();
        assertThat(grphReader.parseGrphFile()).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void undirectedNodes() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/undirectedNode.grph")));
        GrphStructure expectedStructure = GrphBuilder.builder("graph", false)
                .node1("a").node2("b").weight(0).next()
                .node1("b").node2("c").weight(0).next()
                .node1("c").node2("a").weight(0).next()
                .grph();
        assertThat(grphReader.parseGrphFile()).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void weightedNodes() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/weightedNodes.grph")));
        GrphStructure expectedStructure = GrphBuilder.builder("graph", true)
                .node1("Hamburg").node2("Greifswald").weight(372).next()
                .node1("Greifswald").node2("Zehdenick").weight(210).next()
                .node1("Zehdenick").node2("Hamburg").weight(414).next()
                .grph();
        assertThat(grphReader.parseGrphFile()).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void spacedNodeNames() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/spacedNodeNames.grph")));
        GrphStructure expectedStructure = GrphBuilder.builder("graph", true)
                .node1("Hamburg").node2("Greifswald").weight(372).next()
                .node1("Greifswald").node2("Zehdenick").weight(210).next()
                .node1("Zehdenick").node2("Hamburg").weight(414).next()
                .grph();
        assertThat(grphReader.parseGrphFile()).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void nodesWithAttributes() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/nodesWithAttributes.grph")));
        GrphStructure expectedStructure = GrphBuilder.builder("graph", true)
                .node1("a").attr1("1").node2("b").attr2("2").weight(0).next().grph();
        assertThat(grphReader.parseGrphFile()).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void edgeWithLabel() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/edgeWithLabel.grph")));
        GrphStructure expectedStructure = GrphBuilder.builder("graph", true)
                .node1("a").node2("b").edge("a zu b").weight(0).next()
                .node1("b").node2("c").edge("b zu c").weight(0).next()
                .node1("c").node2("a").edge("c zu a").weight(0).next()
                .grph();
        assertThat(grphReader.parseGrphFile()).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void sanitizedInput() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/sanitizedInput.grph")));
        GrphStructure expectedStructure = GrphBuilder.builder("graph", true)
                .node1("Hamburg").node2("Greifswald").weight(372).next()
                .node1("Greifswald").attr1("MK").node2("Zehdenick").weight(210).next()
                .node1("Zehdenick").node2("Hamburg").edge("HH").weight(414).next()
                .grph();
        assertThat(grphReader.parseGrphFile()).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void treeGraph() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/treeGraph.grph")));
        GrphStructure expectedStructure = GrphBuilder.builder("graph", true)
                .node1("a").node2("b").weight(2).next()
                .node1("a").node2("c").weight(10).next()
                .grph();
        assertThat(grphReader.parseGrphFile()).isEqualTo(expectedStructure);
    }

    @Test
    @SneakyThrows
    public void errorUndefinedType() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/errorUndefinedType.grph")));
        assertThrows(IllegalArgumentException.class, grphReader::parseGrphFile);
    }

    @Test
    public void errorFormatInBody() {
        GrphReader grphReader = new GrphReader(new Scanner(readFile("grph/errorFormatInBody.grph")));
        assertThrows(IllegalArgumentException.class, grphReader::parseGrphFile);
    }

}