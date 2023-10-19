package de.haw_hamburg.gka.aufg1.storage;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.Scanner;

@RequiredArgsConstructor
public class GrphGraphStorage {

    public GrphStructure readFrom(File file) throws FileNotFoundException {
        final Scanner graphScanner = new Scanner(file);
        graphScanner.useDelimiter(System.lineSeparator());
        final GrphReader parser = new GrphReader(graphScanner);
        return parser.parseGrphFile();
    }

    public void storeTo(GrphStructure structure, File file) throws FileNotFoundException {
        try (FileWriter fw = new FileWriter(file)) {
            GrphWriter gw = new GrphWriter(structure, new PrintWriter(fw));
            gw.write();
        } catch (IOException ex) {
            throw new FileNotFoundException("Could not find File");
        }
    }
}
