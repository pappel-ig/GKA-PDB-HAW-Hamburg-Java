package de.haw_hamburg.gka;

import org.graphstream.graph.Element;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

public class GraphHelper {

    public static int abstand(Node node) {
        return node.getAttribute("abstand", Integer.class);
    }

    public static int weight(Element edge) {
        return edge.getAttribute("weight", Integer.class);
    }

}
