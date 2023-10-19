package de.haw_hamburg.gka.algo.aufg2;

import de.haw_hamburg.gka.GraphBuilder;
import de.haw_hamburg.gka.aufg2.KruskalAlgorithm;
import de.haw_hamburg.gka.aufg2.MsfResult;
import de.haw_hamburg.gka.aufg1.storage.GrphGraphStorage;
import lombok.SneakyThrows;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class KruskalAlgorithmTest {

    @Nested
    class Randomized {

        private static Stream<Arguments> randomOptions() {
            return Stream.of(
                    Arguments.of(100, 5, true, true, 100),
                    Arguments.of(100, 5, true, false, 1000),
                    Arguments.of(100, 5, false, true, 100),
                    Arguments.of(100, 5, false, false, 1000),
                    Arguments.of(1000, 5, true, true, 100),
                    Arguments.of(1000, 5, true, false, 1000),
                    Arguments.of(1000, 5, false, true, 100),
                    Arguments.of(1000, 5, false, false, 1000),
                    Arguments.of(1000, 2, true, true, 100),
                    Arguments.of(1000, 2, true, false, 1000),
                    Arguments.of(1000, 2, false, true, 100),
                    Arguments.of(1000, 2, false, false, 1000)
            );
        }

        @MethodSource("randomOptions")
        @ParameterizedTest
        void randomPath(int size, int avgDegree, boolean remove, boolean directed, int maxWeight) {
            Graph graf = randomGraph(size, avgDegree, remove, directed, maxWeight);
            KruskalAlgorithm algorithm = new KruskalAlgorithm(graf);

            MsfResult actual = algorithm.msf();
            Kruskal referenceAlgorithm = new Kruskal();
            referenceAlgorithm.setWeightAttribute("weight");
            referenceAlgorithm.init(graf);
            referenceAlgorithm.compute();

            assertThat(actual.getTotalWeight()).isEqualTo((int)referenceAlgorithm.getTreeWeight());
        }

        private Graph randomGraph(int size, int averageDegree, boolean allowRemove, boolean directed, int weight) {
            Graph graph = new MultiGraph("graph");
            RandomGenerator generator = new RandomGenerator(averageDegree, allowRemove, directed, null, null);
            generator.addEdgeAttribute("weight", random -> random.nextInt(weight)+1);
            generator.addSink(graph);
            generator.begin();
            for (int i = 0; i < size; i++) {
                generator.nextEvents();
            }
            generator.end();
            return graph;
        }
    }

    @Test
    void emptyGraph() {
        KruskalAlgorithm algorithm = new KruskalAlgorithm(GraphBuilder.builder("id", false).graph());
        MsfResult msf = algorithm.msf();

        assertThat(msf.getMsf()).isEmpty();
        assertThat(msf.getTotalWeight()).isEqualTo(0);
    }

    @Test
    void disjunkteZusammenhangskomponenten() {
        KruskalAlgorithm algorithm = new KruskalAlgorithm(
                GraphBuilder.builder("id", false)
                        .node1("A").node2("B").next()
                        .node1("1").node2("2").next()
                        .graph());
        MsfResult msf = algorithm.msf();

        assertThat(msf.getMsf()).extracting(Edge::getSourceNode).extracting(Node::getId).isEqualTo(List.of("A", "1"));
        assertThat(msf.getMsf()).extracting(Edge::getTargetNode).extracting(Node::getId).isEqualTo(List.of("B", "2"));
        assertThat(msf.getTotalWeight()).isEqualTo(2);
    }

    @Test
    void directedGraphIsHandledAsUndirected() {
        KruskalAlgorithm algorithm = new KruskalAlgorithm(
                GraphBuilder.builder("id", true)
                        .node1("A").node2("B").next()
                        .node1("1").node2("2").next()
                        .graph());
        MsfResult msf = algorithm.msf();

        assertThat(msf.getMsf()).extracting(Edge::getSourceNode).extracting(Node::getId).isEqualTo(List.of("A", "1"));
        assertThat(msf.getMsf()).extracting(Edge::getTargetNode).extracting(Node::getId).isEqualTo(List.of("B", "2"));
        assertThat(msf.getTotalWeight()).isEqualTo(2);
    }

    @Test
    void singleNodesWithoutEdges() {
        KruskalAlgorithm algorithm = new KruskalAlgorithm(
                GraphBuilder.builder("id", false)
                        .node1("A").next()
                        .node1("B").next()
                        .node1("C").next()
                        .graph());
        MsfResult msf = algorithm.msf();

        assertThat(msf.getMsf()).isEmpty();
        assertThat(msf.getTotalWeight()).isEqualTo(0);
    }

    @Test
    void shouldCalculateWeights() {
        KruskalAlgorithm algorithm = new KruskalAlgorithm(
                GraphBuilder.builder("id", false)
                        .node1("A").node2("B").weight(5).next()
                        .node1("1").node2("2").next()
                        .graph());
        MsfResult msf = algorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(6);
    }

    @Test
    void testEulG1() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("eulG1.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(10);
    }

    @Test
    void testEulG2() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("eulG2.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(25);
    }

    @Test
    void testEulG3() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("eulG3.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(9);
    }

    @Test
    void testGraph01() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("graph01.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(11);
    }

    @Test
    void testGraph02() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("graph02.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(10);
    }

    @Test
    void testGraph03() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("graph03.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(2047);
    }

    @Test
    void testGraph04() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("graph04.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(22);
    }

    @Test
    void testGraph05() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("graph05.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(11);
    }

    @Test
    void testGraph06() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("graph06.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(9);
    }

    @Test
    void testGraph07() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("graph07.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(1009);
    }

    @Test
    void testGraph08() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("graph08.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(72);
    }

    @Test
    void testGraph09() {
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(loadGraph("graph09.grph"));
        MsfResult msf = kruskalAlgorithm.msf();

        assertThat(msf.getTotalWeight()).isEqualTo(12);
    }

    @SneakyThrows
    Graph loadGraph(String name) {
        GrphGraphStorage storage = new GrphGraphStorage();
        return storage.readFrom(Path.of(Objects.requireNonNull(this.getClass().getResource("/kruskal/" + name)).toURI()).toFile()).toGraph();
    }
}
