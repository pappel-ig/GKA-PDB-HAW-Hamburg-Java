package de.haw_hamburg.gka;

import lombok.SneakyThrows;

import java.io.File;

public class TestHelper {

    @SneakyThrows
    public static File getFile(String name) {
        return new File(TestHelper.class.getClassLoader().getResource(name).toURI());
    }

}

