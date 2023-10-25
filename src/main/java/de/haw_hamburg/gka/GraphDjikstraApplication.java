package de.haw_hamburg.gka;

import de.haw_hamburg.gka.serializer.GrphGraphSerializer;
import de.haw_hamburg.gka.serializer.GrphStructure;
import lombok.SneakyThrows;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class GraphDjikstraApplication {

    @SneakyThrows
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new MultiGraph(null);
        String str = GraphDjikstraApplication.class.getClassLoader().getResource("directedNode.dgs").getFile();
        FileSource fs = FileSourceFactory.sourceFor(str);
        fs.addSink(graph);
        fs.begin(str);
        while (fs.nextEvents()) {

        }
        fs.end();
        fs.removeSink(graph);
        System.out.println("###");
        System.out.println(graph.getId());
        System.out.println("###");
        graph.display();
    }

}
