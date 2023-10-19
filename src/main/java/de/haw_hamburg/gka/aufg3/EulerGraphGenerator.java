package de.haw_hamburg.gka.aufg3;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EulerGraphGenerator {

    private final Random random = new Random();

    public Graph createEulMultiGraph(int n, int m) {
        if (n > m) throw new RuntimeException("Cant create graph with m > n");

        final Graph graph = new MultiGraph(UUID.randomUUID().toString(), false, true, n, m);
        final List<Integer> p = IntStream.range(1, m+1).boxed().collect(Collectors.toList());
        final Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            int k = random.nextInt(0, p.size());
            pos.put(p.remove(k), i);
        }
        int start;
        if (!pos.containsKey(1)) {
            start = random.nextInt(1,n+1);
            pos.put(1, pos.get(start));
        } else {
            start = pos.get(1);
        }
        int cur = start;
        for (int i = 2; i <= m; i++) {
            int nxt;
            if (!pos.containsKey(i)) {
                nxt = random.nextInt(1,n+1);
            } else {
                nxt = pos.get(i);
            }
            graph.addEdge(String.format("%s:%s;%s", cur, nxt, UUID.randomUUID()), String.valueOf(cur), String.valueOf(nxt));
            cur = nxt;
        }
        graph.addEdge(String.format("%s:%s;%s", cur, start, UUID.randomUUID()), String.valueOf(cur), String.valueOf(start));
        return graph;
    }

}
