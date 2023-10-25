package de.haw_hamburg.gka;

import de.haw_hamburg.gka.serializer.GrphGraphSerializer;
import de.haw_hamburg.gka.serializer.GrphStructure;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class GraphDjikstraApplication {

    public static void main(String[] args) throws FileNotFoundException {
        GrphGraphSerializer serializer = new GrphGraphSerializer();
        GrphStructure grphStructure = serializer.readFrom(Paths.get("src/test/resources/graph01.grph").toFile());
        GrphStructure grphStructure1 = serializer.readFrom(Paths.get("src/test/resources/graph02.grph").toFile());
        GrphStructure grphStructure2 = serializer.readFrom(Paths.get("src/test/resources/graph03.grph").toFile());
        GrphStructure grphStructure3 = serializer.readFrom(Paths.get("src/test/resources/graph04.grph").toFile());
        GrphStructure grphStructure4 = serializer.readFrom(Paths.get("src/test/resources/graph05.grph").toFile());
        GrphStructure grphStructure5 = serializer.readFrom(Paths.get("src/test/resources/graph06.grph").toFile());
        GrphStructure grphStructure7 = serializer.readFrom(Paths.get("src/test/resources/graph08.grph").toFile());
        GrphStructure grphStructure8 = serializer.readFrom(Paths.get("src/test/resources/graph09.grph").toFile());
        System.out.println();
    }

}
