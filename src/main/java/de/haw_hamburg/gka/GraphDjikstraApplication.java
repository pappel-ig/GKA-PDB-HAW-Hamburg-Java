package de.haw_hamburg.gka;

import de.haw_hamburg.gka.serializer.GrphGraphSerializer;
import lombok.SneakyThrows;
import org.graphstream.graph.Graph;

import java.io.File;

public class GraphDjikstraApplication {

    @SneakyThrows
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        GrphGraphSerializer serializer = new GrphGraphSerializer();
        Graph graph = serializer.readFrom(ResourceLoadHelper.loadFile("graph01.grph")).toGraph();
        graph.display();
    }

}
