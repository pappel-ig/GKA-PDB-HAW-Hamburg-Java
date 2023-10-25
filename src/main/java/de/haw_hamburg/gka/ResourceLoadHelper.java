package de.haw_hamburg.gka;

import lombok.SneakyThrows;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ResourceLoadHelper {

    @SneakyThrows
    public static String loadString(String name) {
        final URI resource = Objects.requireNonNull(ResourceLoadHelper.class.getClassLoader().getResource(name), String.format("resource with name '%s' not found", name)).toURI();
        return Files.readString(Path.of(resource));
    }

    @SneakyThrows
    public static File loadFile(String name) {
        final URI resource = Objects.requireNonNull(ResourceLoadHelper.class.getClassLoader().getResource(name), String.format("resource with name '%s' not found", name)).toURI();
        return new File(resource);
    }

}
