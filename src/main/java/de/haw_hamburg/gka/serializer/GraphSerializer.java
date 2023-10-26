package de.haw_hamburg.gka.serializer;

import org.graphstream.graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface GraphSerializer {

    GrphStructure readFrom(File file) throws IOException;
    InputStream saveTo(Graph graph);

}
