package ru.kolosov.lab1.repository;

import ru.kolosov.lab1.model.CharacterRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.Path;

public class CharacterCsvRepository {
    private final Path csvFile;

    public CharacterCsvRepository(Path csvFile) {
        this.csvFile = csvFile;
    }

    public List<CharacterRecord> findAll() throws IOException {
    List<CharacterRecord> characters = new ArrayList<>();

    try (BufferedReader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8)) {

        // Пропускаем заголовок CSV
        reader.readLine();

        String line;

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",", -1);

            if (columns.length < 9) {
                throw new IllegalArgumentException(
                        "Incorrect CSV row: " + line
                );
            }

            CharacterRecord character = parseCharacter(columns);
            characters.add(character);
        }
    }

    return characters;
    }

    private CharacterRecord parseCharacter(String[] columns) {
    return new CharacterRecord(
            Long.parseLong(columns[0]),
            columns[1],
            columns[2],
            columns[3],
            columns[4],
            columns[5],
            columns[6],
            columns[7],
            columns[8]
    );
    }
}
