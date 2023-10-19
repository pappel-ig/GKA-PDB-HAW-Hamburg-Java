package de.haw_hamburg.gka.aufg2;

import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class GraphGenerator {

    public Graph randomGraph(int size, int avgDegree, int maxWeight, boolean allowRemove, boolean directed) {
        Graph graph = new MultiGraph("graph");
        RandomGenerator generator = new RandomGenerator(avgDegree, allowRemove, directed, null, null);
        generator.addEdgeAttribute("weight", random -> random.nextInt(maxWeight)+1);
        generator.addSink(graph);
        generator.begin();
        for (int i = 0; i < size; i++) {
            generator.nextEvents();
        }
        generator.end();
        return graph;
    }
}
