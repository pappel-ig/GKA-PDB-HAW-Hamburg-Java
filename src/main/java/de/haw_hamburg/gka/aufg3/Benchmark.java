package de.haw_hamburg.gka.aufg3;

import lombok.Value;
import org.graphstream.graph.Graph;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.Executors;

public class Benchmark {

    public static final String HEADER = "id\tknoten\tkanten\tms\tmsPerElem\n";

    public static void main(String[] args) {
        final Benchmark benchmark = new Benchmark();
        benchmark.benchmark(100000, 100000, 100000, 200000, 200, 1);
    }

    private void printResult(String result) {
        System.out.print(result);
    }

    private Result averageResult(List<Result> results) {
        Result result = results.get(0);
        double avgMs = results.stream().mapToDouble(Result::getMs).average().getAsDouble();
        double avgElemMs = results.stream().mapToDouble(Result::getMsElem).average().getAsDouble();
        return new Result(result.id, result.knoten, result.kanten, avgMs, avgElemMs);
    }

    private Result hierholzer(Graph graph) {
        final Hierholzer algo = new Hierholzer();
        long start = System.currentTimeMillis();
        algo.euler(graph);
        long ms = System.currentTimeMillis() - start;
        return new Result(graph.getAttribute("id", Integer.class), graph.getNodeCount(), graph.getEdgeCount(), ms, (double) ms /graph.getEdgeCount());
    }

    private Flux<Graph> fluxSink(int startKnoten, int stepKnoten, int startKanten, int stepKanten, int iterations, int repeats) {
        return Flux.create(graphFluxSink -> {
            final EulerGraphGenerator generator = new EulerGraphGenerator();
            int knoten = startKnoten;
            int kanten = startKanten;
            for (int i = 0; i < iterations; i++) {
                final Graph eulMultiGraph = generator.createEulMultiGraph(knoten, kanten);
                eulMultiGraph.setAttribute("id", i+1);
                knoten += stepKnoten;
                kanten += stepKanten;
                for (int j = 0; j < repeats; j++) {
                    graphFluxSink.next(eulMultiGraph);
                }
            }
        });
    }

    private Flux<Result> aggregateResults(GroupedFlux<Integer, Result> groupedResults, int repeats) {
        return groupedResults
                .buffer(repeats)
                .map(this::averageResult);
    }

    public void benchmark(int startKnoten, int stepKnoten, int startKanten, int stepKanten, int iterations, int repeats) {
        fluxSink(startKnoten, stepKnoten, startKanten, stepKanten, iterations, repeats)
                .subscribeOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())))
                .map(this::hierholzer)
                .groupBy(Result::getId)
                .flatMap(groups -> aggregateResults(groups, repeats))
                .map(Result::toString)
                .mergeWith(Flux.just(HEADER))
                .doOnNext(this::printResult)
                .subscribe();
    }

    @Value
    static class Result {
        int id;
        int knoten;
        int kanten;
        double ms;
        double msElem;

        @Override
        public String toString() {
            return String.format("%s\t%s\t%s\t%.0f\t%.4f%n", id, knoten, kanten, ms, msElem);
        }
    }
}
