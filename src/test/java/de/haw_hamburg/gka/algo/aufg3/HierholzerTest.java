package de.haw_hamburg.gka.algo.aufg3;

import de.haw_hamburg.gka.aufg3.EulerGraphGenerator;
import de.haw_hamburg.gka.aufg3.Hierholzer;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HierholzerTest {

    @Nested
    class Randomized {

        final static EulerGraphGenerator generator = new EulerGraphGenerator();

        static Stream<Arguments> eulerGraphen() {
            return Stream.of(
                    Arguments.of("100 Knoten und 1000 Kanten", 5, 10),
                    Arguments.of("200 Knoten und 2000 Kanten", 200, 2000),
                    Arguments.of("300 Knoten und 3000 Kanten", 300, 3000),
                    Arguments.of("400 Knoten und 4000 Kanten", 400, 4000),
                    Arguments.of("500 Knoten und 5000 Kanten", 500, 5000),

                    Arguments.of("100 Knoten und 3000 Kanten", 100, 3000),
                    Arguments.of("200 Knoten und 3000 Kanten", 200, 3000),
                    Arguments.of("300 Knoten und 3000 Kanten", 300, 3000),
                    Arguments.of("400 Knoten und 3000 Kanten", 400, 3000),
                    Arguments.of("500 Knoten und 3000 Kanten", 500, 3000),

                    Arguments.of("1000 Knoten und 1000 Kanten", 1000, 1000),
                    Arguments.of("2000 Knoten und 2000 Kanten", 2000, 2000),
                    Arguments.of("3000 Knoten und 3000 Kanten", 3000, 3000),
                    Arguments.of("4000 Knoten und 4000 Kanten", 4000, 4000),
                    Arguments.of("5000 Knoten und 5000 Kanten", 5000, 5000),

                    Arguments.of("2500 Knoten und 100.000 Kanten", 2500, 100000),
                    Arguments.of("2500 Knoten und 200.000 Kanten", 2500, 200000),
                    Arguments.of("2500 Knoten und 1.000.000 Kanten", 2500, 1000000)
            );
        }

        @MethodSource("eulerGraphen")
        @ParameterizedTest(name = "{0}")
        void randomEulerGraphTest(String unused, int knoten, int kanten) {
            final Graph graph = generator.createEulMultiGraph(knoten, kanten);
            long n = System.currentTimeMillis();
            final List<Node> eulericPath = new Hierholzer().euler(graph);
            System.out.println(System.currentTimeMillis() - n + "ms");
            assertThat(graph.getEdgeCount()).isEqualTo(kanten);
            assertThat(eulericPath).size().isEqualTo(kanten+1);
            validateEulerianCycle(eulericPath);
        }
    }

    @Test
    void triangle() {
        Graph graph = new MultiGraph("graph", false, true);
        graph.addEdge("ab", "a", "b");
        graph.addEdge("bc", "b", "c");
        graph.addEdge("ca", "c", "a");

        final List<Node> eulericPath = new Hierholzer().euler(graph);
        System.out.println(eulericPath);
        assertThat(eulericPath).isNotEmpty();
        assertThat(eulericPath).size().isEqualTo(4);
        validateEulerianCycle(eulericPath);
    }

    @Test
    void simpleCircle() {
        Graph graph = new MultiGraph("graph", false, true);
        graph.addEdge("ab", "a", "b");
        graph.addEdge("bc", "b", "c");
        graph.addEdge("cd", "c", "d");
        graph.addEdge("de", "d", "e");
        graph.addEdge("ea", "e", "a");

        final List<Node> eulericPath = new Hierholzer().euler(graph);
        System.out.println(eulericPath);
        assertThat(eulericPath).isNotEmpty();
        assertThat(eulericPath).size().isEqualTo(6);
    }

    @Test
    void complex() {
        Graph graph = new MultiGraph("graph", false, true);
        graph.addEdge("1", "4", "3");
        graph.addEdge("2", "2", "1");
        graph.addEdge("3", "3", "2");
        graph.addEdge("4", "3", "1");
        graph.addEdge("5", "2", "4");
        graph.addEdge("6", "1", "3");
        graph.addEdge("7", "4", "4");
        graph.addEdge("8", "1", "5");
        graph.addEdge("9", "5", "2");
        graph.addEdge("10", "3", "3");

        final List<Node> eulericPath = new Hierholzer().euler(graph);
        System.out.println(eulericPath);
        assertThat(eulericPath).isNotEmpty();
        assertThat(eulericPath).size().isEqualTo(11);
        validateEulerianCycle(eulericPath);
    }

    @Test
    void schleifenWerdenGegangen() {
        Graph graph = new MultiGraph("graph", false, true);
        graph.addEdge("loop1", "a", "a");
        graph.addEdge("loop2", "a", "a");
        graph.addEdge("loop3", "a", "a");
        graph.addEdge("loop4", "a", "a");

        final List<Node> eulericPath = new Hierholzer().euler(graph);
        System.out.println(eulericPath);
        assertThat(eulericPath).isNotEmpty();
        assertThat(eulericPath).size().isEqualTo(5);
        validateEulerianCycle(eulericPath);
    }

    @Test
    void sanduhr() {
        Graph graph = new MultiGraph("graph", false, true);
        graph.addEdge("OLOR", "OL", "OR");
        graph.addEdge("OLM", "OL", "M");
        graph.addEdge("ORM", "OR", "M");
        graph.addEdge("ULUR", "UL", "UR");
        graph.addEdge("ULM", "UL", "M");
        graph.addEdge("URM", "UR", "M");

        final List<Node> eulericPath = new Hierholzer().euler(graph);
        System.out.println(eulericPath);
        assertThat(eulericPath).isNotEmpty();
        assertThat(eulericPath).size().isEqualTo(7);
        validateEulerianCycle(eulericPath);
    }

    @Test
    void mehrereZusammenhangskomponenten() {
        Graph graph = new MultiGraph("graph", false, true);
        // Zusammenhangskomponente 1
        graph.addEdge("ab", "a", "b");
        graph.addEdge("bc", "b", "c");
        graph.addEdge("ca", "c", "a");
        // Zusammenhangskomponente 2
        graph.addEdge("xy", "x", "y");
        graph.addEdge("yz", "y", "z");
        graph.addEdge("zx", "z", "x");

        final List<Node> eulericPath = new Hierholzer().euler(graph);
        validateEulerianCycle(eulericPath);
    }

    @Test
    void leererGraph() {
        Graph graph = new MultiGraph("graph", false, true);
        final List<Node> eulericPath = new Hierholzer().euler(graph);
        System.out.println(eulericPath);
        assertThat(eulericPath).isEmpty();
    }

    @Test
    void nurKnoten() {
        Graph graph = new MultiGraph("graph", false, true);
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        final List<Node> eulericPath = new Hierholzer().euler(graph);
        System.out.println(eulericPath);
        assertThat(eulericPath).isEmpty();
    }

    @Test
    void linieIstNichtEulersch() {
        Graph graph = new MultiGraph("graph", false, true);
        graph.addEdge("ab", "a", "b");

        final List<Node> eulericPath = new Hierholzer().euler(graph);
        assertThrows(RuntimeException.class, () -> {
            validateEulerianCycle(eulericPath);
        });
    }

    @Test
    void hausVomNikolaus() {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new MultiGraph("graph", false, true);
        graph.addEdge("LORO", "LO", "RO");
        graph.addEdge("LURU", "LU", "RU");
        graph.addEdge("LULO", "LU", "LO");
        graph.addEdge("RURO", "RU", "RO");
        graph.addEdge("LORU", "LO", "RU");
        graph.addEdge("LURO", "LU", "RO");
        graph.addEdge("LOD", "LO", "D");
        graph.addEdge("ROD", "RO", "D");

        final List<Node> eulericPath = new Hierholzer().euler(graph);
        System.out.println(eulericPath);
        assertThrows(RuntimeException.class, () -> {
            validateEulerianCycle(eulericPath);
        });
    }

    void validateEulerianCycle(List<Node> cycle) {
        Set<Edge> visitedEdges = new LinkedHashSet<>();
        for (int i = 0; i < cycle.size()-1; i++) {
            Node source = cycle.get(i);
            Node target = cycle.get(i+1);

            // Jeder Knoten kann nur eine gerade Anzahl von Kanten haben
            if (source.edges().mapToInt(value -> value.isLoop() ? 2 : 1).sum() % 2 != 0) {
                throw new RuntimeException("Odd Kanten");
            }

            // Schleife benutzen
            if (source == target) {
                Edge edge = source.edges()
                        .filter(e -> !visitedEdges.contains(e))
                        .filter(Edge::isLoop)
                        .findAny()
                        .orElseThrow(() -> new RuntimeException(source + "-" + target));
                visitedEdges.add(edge);
            // Normale Kante benutzen
            } else {
                Edge edgeBetween = source.edges()
                        .filter(edge -> !visitedEdges.contains(edge))
                        .filter(edge -> edge.getTargetNode() == target || edge.getSourceNode() == target)
                        .findAny()
                        .orElseThrow(() -> new RuntimeException(source + "-" + target));
                visitedEdges.add(edgeBetween);
            }
        }
    }
}