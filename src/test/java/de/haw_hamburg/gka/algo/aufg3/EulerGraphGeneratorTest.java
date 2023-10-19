package de.haw_hamburg.gka.algo.aufg3;

import de.haw_hamburg.gka.aufg3.EulerGraphGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EulerGraphGeneratorTest {
    final EulerGraphGenerator generator = new EulerGraphGenerator();

    @Test
    void anzahlKantenUndKnoten() {
        final Graph eulMultiGraph = generator.createEulMultiGraph(130, 260);

        assertThat(eulMultiGraph.getNodeCount()).isEqualTo(130);
        assertThat(eulMultiGraph.getEdgeCount()).isEqualTo(260);
    }

    @Test
    void istEulerKreis() {
        final Graph eulMultiGraph = generator.createEulMultiGraph(10000, 30000);

        eulMultiGraph.nodes().map(this::degree).forEach(integer -> {
            assertThat(integer).isEven();
        });
    }

    int degree(Node node) {
        return node.edges().mapToInt(value -> value.getSourceNode() == value.getTargetNode() ? 2 : 1).sum();
    }

}