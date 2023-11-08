package de.haw_hamburg.gka.storage;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

@RequiredArgsConstructor
public class GrphWriter {

    private final GrphStructure structure;
    private final Writer writer;

    public void write() {
        try {
            writer.write(header());
            for (GrphLine line : structure.getGrphLines()) {
                writer.write(line(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Kann nicht ins File schreiben", e);
        }
    }

    private String header() {
        return String.format("#%s:%s;%n", structure.isDirected() ? "directed" : "undirected", structure.getName());
    }

    private String line(GrphLine grphLine) {
        String node1 = grphLine.getNode1();
        String attr1 = Objects.nonNull(grphLine.getAttr1()) ? String.format(":%s", grphLine.getAttr1()) : "";
        String node2 = Objects.nonNull(grphLine.getNode2()) ? String.format("-%s", grphLine.getNode2()) : "";
        String attr2 = Objects.nonNull(grphLine.getAttr2()) ? String.format(":%s", grphLine.getAttr2()) : "";
        String edge = Objects.nonNull(grphLine.getEdge()) ? String.format(" (%s)", grphLine.getEdge()) : "";
        String weight = grphLine.getWeight() != 1 ? String.format("::%d", grphLine.getWeight()) : "";
        return String.format("%s%s%s%s%s%s;%n", node1, attr1, node2, attr2, edge, weight);
    }


}

