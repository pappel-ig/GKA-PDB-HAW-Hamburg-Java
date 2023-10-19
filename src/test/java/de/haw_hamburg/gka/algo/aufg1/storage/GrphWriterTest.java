package de.haw_hamburg.gka.algo.aufg1.storage;

import de.haw_hamburg.gka.GrphBuilder;
import de.haw_hamburg.gka.TestHelper;
import de.haw_hamburg.gka.aufg1.storage.GrphStructure;
import de.haw_hamburg.gka.aufg1.storage.GrphWriter;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class GrphWriterTest {

    @Test
    public void mapAll() {
        StringWriter stringWriter = new StringWriter();
        GrphStructure structure = GrphBuilder.builder("graph", false)
                .node1("a").attr1("atr1").node2("b").attr2("atr2").edge("edge").weight(5).next().grph();
        GrphWriter grphWriter = new GrphWriter(structure, stringWriter);
        grphWriter.write();

        assertThat(stringWriter.toString()).isEqualTo(TestHelper.readFile("writer/mapAll.grph"));
    }

    @Test
    public void mapWithoutOptional() {
        StringWriter stringWriter = new StringWriter();
        GrphStructure structure = GrphBuilder.builder("graph", true)
                .node1("a").next().grph();
        GrphWriter grphWriter = new GrphWriter(structure, stringWriter);
        grphWriter.write();

        assertThat(stringWriter.toString()).isEqualTo(TestHelper.readFile("writer/mapWithoutOptional.grph"));
    }

}