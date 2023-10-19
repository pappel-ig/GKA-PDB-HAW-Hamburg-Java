package de.haw_hamburg.gka.algo.aufg1;

import de.haw_hamburg.gka.GraphBuilder;
import de.haw_hamburg.gka.aufg1.DijkstraAlgorithm;
import lombok.SneakyThrows;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DijkstraAlgorithmTest {

    @Nested
    class Randomized {

        private static Stream<Arguments> randomOptions() {
            return Stream.of(
                    Arguments.of(100, 5, true, true),
                    Arguments.of(100, 5, true, false),
                    Arguments.of(100, 5, false, true),
                    Arguments.of(100, 5, false, false),
                    Arguments.of(1000, 5, true, true),
                    Arguments.of(1000, 5, true, false),
                    Arguments.of(1000, 5, false, true),
                    Arguments.of(1000, 5, false, false),
                    Arguments.of(1000, 2, true, true),
                    Arguments.of(1000, 2, true, false),
                    Arguments.of(1000, 2, false, true),
                    Arguments.of(1000, 2, false, false)
            );
        }

        @MethodSource("randomOptions")
        @ParameterizedTest
        void randomPath(int size, int avgDegree, boolean remove, boolean directed) {
            DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
            Graph graf = randomGraph(size, avgDegree, remove, directed);
            Path actual = algorithm.getPathTo(graf.getNode(0), graf.getNode(graf.getNodeCount() - 1));
            Path expected = computeBestPath(graf.getNode(0), graf.getNode(graf.getNodeCount() - 1));

            System.out.printf("actual: %s%n", actual);
            System.out.printf("expect: %s%n", expected);

            assertThat(calculcateTotalLength(actual)).isEqualTo(calculcateTotalLength(expected));
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        private int calculcateTotalLength(Path path) {
            return path.edges().reduce(0, (integer, edge) -> integer+edge.getAttribute("weight", Integer.class), Integer::sum);
        }

        private Path computeBestPath(Node start, Node end) {
            Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, "result", "weight");
            dijkstra.init(start.getGraph());
            dijkstra.setSource(start);
            dijkstra.compute();
            return dijkstra.getPath(end);
        }

        private Graph randomGraph(int size, int averageDegree, boolean allowRemove, boolean directed) {
            Graph graph = new MultiGraph("graph");
            RandomGenerator generator = new RandomGenerator(averageDegree, allowRemove, directed, null, null);
            generator.addEdgeAttribute("weight", random -> random.nextInt(100)+1);
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
    @SneakyThrows
    public void allEdgesAreConsidered() {
        final Graph graph = GraphBuilder.builder("graph", true)
                .node1("A").node2("B").weight(2).next()
                .node1("B").node2("D").weight(2).next()
                .node1("D").node2("E").weight(1000).next()
                .node1("D").node2("F").weight(2).next()
                .node1("B").node2("C").weight(100).next()
                .node1("F").node2("C").weight(3).next()
                .node1("A").node2("C").weight(10).next()
                .graph();

        final DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        algorithm.getPathTo(graph.getNode("A"), graph.getNode("F"));

        vorgaenger(graph, "A", "A");
        vorgaenger(graph, "B", "A");
        vorgaenger(graph, "D", "B");
        vorgaenger(graph, "E", "D");
        vorgaenger(graph, "F", "D");
        vorgaenger(graph, "C", "F");

        entfernung(graph, "A", 0);
        entfernung(graph, "B", 2);
        entfernung(graph, "D", 4);
        entfernung(graph, "E", 1004);
        entfernung(graph, "F", 6);
        entfernung(graph, "C", 9);
    }

    @Test
    @SneakyThrows
    public void firstPathCanOverrideLongPath() {
        final Graph graph = GraphBuilder.builder("graph", true)
                .node1("A").node2("B").weight(2).next()
                .node1("B").node2("D").weight(2).next()
                .node1("D").node2("E").weight(1000).next()
                .node1("D").node2("F").weight(3).next()
                .node1("B").node2("C").weight(100).next()
                .node1("F").node2("C").weight(3).next()
                .node1("A").node2("C").weight(10).next()
                .graph();

        final DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        algorithm.getPathTo(graph.getNode("A"), graph.getNode("F"));

        vorgaenger(graph, "A", "A");
        vorgaenger(graph, "B", "A");
        vorgaenger(graph, "D", "B");
        vorgaenger(graph, "E", "D");
        vorgaenger(graph, "F", "D");
        vorgaenger(graph, "C", "A");

        entfernung(graph, "A", 0);
        entfernung(graph, "B", 2);
        entfernung(graph, "D", 4);
        entfernung(graph, "E", 1004);
        entfernung(graph, "F", 7);
        entfernung(graph, "C", 10);
    }

    @Test
    @SneakyThrows
    public void shouldNotTraverseInDirectedGraph() {
        final Graph graph = GraphBuilder.builder("graph", true)
                .node1("B").node2("A").next()
                .node1("B").node2("C").next()
                .node1("C").node2("A").next()
                .graph();

        final DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        algorithm.getPathTo(graph.getNode("A"), graph.getNode("C"));

        vorgaenger(graph, "A", "A");
        vorgaenger(graph, "B", null);
        vorgaenger(graph, "C", null);

        entfernung(graph, "A", 0);
        entfernung(graph, "B", Integer.MAX_VALUE);
        entfernung(graph, "C", Integer.MAX_VALUE);
    }

    @Test
    @SneakyThrows
    public void noPathExists() {
        final Graph graph = GraphBuilder.builder("graph", true)
                .node1("A").node2("B").next()
                .node1("C").node2("D").next()
                .node1("D").node2("C").next()
                .graph();

        final DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        algorithm.getPathTo(graph.getNode("A"), graph.getNode("B"));

        vorgaenger(graph, "A", "A");
        vorgaenger(graph, "B", "A");
        vorgaenger(graph, "C", null);
        vorgaenger(graph, "D", null);

        entfernung(graph, "A", 0);
        entfernung(graph, "B", 1);
        entfernung(graph, "C", Integer.MAX_VALUE);
        entfernung(graph, "D", Integer.MAX_VALUE);
    }

    @Test
    @SneakyThrows
    public void twoIdealPaths() {
        Graph graph = GraphBuilder.builder("graph", true)
                .node1("A").node2("B").next()
                .node1("A").node2("C").next()
                .node1("B").node2("D").next()
                .node1("C").node2("E").next()
                .node1("D").node2("F").next()
                .node1("E").node2("F").next()
                .graph();

        final DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        algorithm.getPathTo(graph.getNode("A"), graph.getNode("F"));

        vorgaenger(graph, "A", "A");
        vorgaenger(graph, "B", "A");
        vorgaenger(graph, "C", "A");
        vorgaenger(graph, "D", "B");
        vorgaenger(graph, "E", "C");
        vorgaenger(graph, "F", "D");

        entfernung(graph, "A", 0);
        entfernung(graph, "B", 1);
        entfernung(graph, "C", 1);
        entfernung(graph, "D", 2);
        entfernung(graph, "E", 2);
        entfernung(graph, "F", 3);
    }

    @Test
    @SneakyThrows
    public void weightShouldBeUsedInCalculation() {
        Graph graph = GraphBuilder.builder("graph", true)
                .node1("A").node2("B").next()
                .node1("A").node2("C").next()
                .node1("B").node2("D").weight(2).next()
                .node1("C").node2("E").next()
                .node1("D").node2("F").next()
                .node1("E").node2("F").next()
                .graph();

        final DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        algorithm.getPathTo(graph.getNode("A"), graph.getNode("F"));

        vorgaenger(graph, "A", "A");
        vorgaenger(graph, "B", "A");
        vorgaenger(graph, "C", "A");
        vorgaenger(graph, "D", "B");
        vorgaenger(graph, "E", "C");
        vorgaenger(graph, "F", "E");

        entfernung(graph, "A", 0);
        entfernung(graph, "B", 1);
        entfernung(graph, "C", 1);
        entfernung(graph, "D", 3);
        entfernung(graph, "E", 2);
        entfernung(graph, "F", 3);
    }

    @Test
    @SneakyThrows
    public void parallelEdges() {
        Graph graph = GraphBuilder.builder("graph", false)
                .node1("A").node2("B").weight(5).next()
                .node1("A").node2("B").weight(2).next()
                .graph();

        final DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        algorithm.getPathTo(graph.getNode("A"), graph.getNode("B"));

        vorgaenger(graph, "B", "A");
        entfernung(graph, "B", 2);
    }

    private void vorgaenger(Graph graph, String node, String expectedVorgaenger) {
        assertThat(graph.getNode(node).getAttribute("vorgänger", Node.class)).isEqualTo(graph.getNode(expectedVorgaenger));
    }

    private void entfernung(Graph graph, String node, Integer entfernung) {
        assertThat(graph.getNode(node).getAttribute("abstand")).isEqualTo(entfernung);
    }

}