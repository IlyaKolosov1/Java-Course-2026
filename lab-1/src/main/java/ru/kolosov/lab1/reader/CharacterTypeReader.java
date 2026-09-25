package ru.kolosov.lab1.reader;

import java.nio.file.Path; // Тип данных
import java.util.HashSet; // Коллекция

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class CharacterTypeReader {

    // public - can be executed from Main
    public HashSet<String> readTypes(Path inputFile) {
        HashSet<String> types = new HashSet<>();

        // try-with-resources
        try (BufferedReader reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8)) {
            // skip first row
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",", -1);

                // check that the type column exists
                if (columns.length <= 4) {
                    throw new IllegalArgumentException("Incorrect CSV row: " + line);
                }

                String type = columns[4].trim();

                if (!type.isBlank()) {
                    types.add(type);
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Can't read file by path: " + inputFile,
                    exception
            );
        }

        return types;
    }
}
