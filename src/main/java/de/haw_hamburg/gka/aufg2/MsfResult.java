package de.haw_hamburg.gka.aufg2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.graphstream.graph.Edge;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MsfResult {

    private final int totalWeight;
    private final List<Edge> msf;

}
