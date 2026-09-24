package ru.kolosov.lab1.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class TypeFileWriter {

    public void writeTypes(Path outputFile, Set<String> types) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                outputFile,
                StandardCharsets.UTF_8
        )) {

            for (String type : types) {
                writer.write(type);
                writer.newLine();
            }
        } catch (IOException exception) {
            System.err.println("Failed to write result file: " + outputFile);
            throw new UncheckedIOException(
                    "Failed to write result file: " + outputFile,
                    exception
            );
        }
    }
}
