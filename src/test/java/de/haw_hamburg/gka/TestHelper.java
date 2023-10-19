package de.haw_hamburg.gka;

import lombok.SneakyThrows;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class TestHelper {
    @SneakyThrows
    public static String readFile(String name) {
        return Files.readString(Path.of(Objects.requireNonNull(TestHelper.class.getClassLoader().getResource(name)).toURI()), Charset.defaultCharset());
    }
}

