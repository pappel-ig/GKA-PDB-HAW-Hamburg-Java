package de.haw_hamburg.gka.algo;

import de.haw_hamburg.gka.GraphBuilder;
import lombok.SneakyThrows;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DjikstraAlgorithmTest {

    @Nested
    class Randomized {

        @Test
        void randomPath() {
            DjikstraAlgorithm algorithm = new DjikstraAlgorithm();
            Graph graf = randomGraph(100, 2, false, true);
            Path actual = algorithm.getPathTo(graf.getNode(0), graf.getNode(graf.getNodeCount() - 1));
            Path expected = computeBestPath(graf.getNode(0), graf.getNode(graf.getNodeCount() - 1));

            assertThat(actual).isEqualTo(expected);
        }

        private Path computeBestPath(Node start, Node end) {
            Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "weight");
            dijkstra.init(start.getGraph());
            dijkstra.setSource(start);
            return dijkstra.getPath(end);
        }

        private Graph randomGraph(int size, int averageDegree, boolean allowRemove, boolean directed) {
            Graph graph = new MultiGraph("graph");
            Generator generator = new RandomGenerator(averageDegree, allowRemove, directed, null, "weight");
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

        final DjikstraAlgorithm algorithm = new DjikstraAlgorithm();
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

        final DjikstraAlgorithm algorithm = new DjikstraAlgorithm();
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

        final DjikstraAlgorithm algorithm = new DjikstraAlgorithm();
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

        final DjikstraAlgorithm algorithm = new DjikstraAlgorithm();
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

        final DjikstraAlgorithm algorithm = new DjikstraAlgorithm();
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

        final DjikstraAlgorithm algorithm = new DjikstraAlgorithm();
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

    private void vorgaenger(Graph graph, String node, String expectedVorgaenger) {
        assertThat(graph.getNode(node).getAttribute("vorgänger", Node.class)).isEqualTo(graph.getNode(expectedVorgaenger));
    }

    private void entfernung(Graph graph, String node, Integer entfernung) {
        assertThat(graph.getNode(node).getAttribute("abstand")).isEqualTo(entfernung);
    }

}