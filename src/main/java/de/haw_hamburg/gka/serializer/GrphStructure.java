package de.haw_hamburg.gka.serializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.graphstream.graph.Graph;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GrphStructure {
    private String name;
    private boolean directed;
    private List<GrphLine> grphLines;

    public Graph toGraph() {
        return null;
    }

    public static GrphStructure from(Graph graph) {
        return null;
    }
}