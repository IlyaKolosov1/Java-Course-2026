package ru.kolosov.lab1.repository;

import ru.kolosov.lab1.model.CharacterRecord;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CharacterCsvRepository {

    private final Path csvFile;

    private static final String CSV_HEADER =
            "id,name,status,species,type,gender,origin/name,location/name,created";

    public CharacterCsvRepository(Path csvFile) {
        this.csvFile = csvFile;
    }

    public List<CharacterRecord> findAll() {
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
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Can't read file by path: " + csvFile,
                    exception
            );
        }

        return characters;
    }

    public Optional<CharacterRecord> findById(long id) {
        List<CharacterRecord> characters = findAll();

        for (CharacterRecord character : characters) {
            if (character.id() == id) {
                return Optional.of(character);
            }
        }

        return Optional.empty();
    }

    public void create(CharacterRecord newCharacter) {
        List<CharacterRecord> characters = findAll();

        for (CharacterRecord character : characters) {
            if (character.id() == newCharacter.id()) {
                throw new IllegalArgumentException(
                        "Character with id "
                                + newCharacter.id()
                                + " already exists"
                );
            }
        }

        characters.add(newCharacter);
        writeAll(characters);
    }

    public boolean update(CharacterRecord updatedCharacter) {
        List<CharacterRecord> characters = findAll();

        for (int i = 0; i < characters.size(); i++) {
            CharacterRecord currentCharacter = characters.get(i);

            if (currentCharacter.id() == updatedCharacter.id()) {
                characters.set(i, updatedCharacter);
                writeAll(characters);
                return true;
            }
        }

        return false;
    }

    public boolean deleteById(long id) {
        List<CharacterRecord> characters = findAll();

        for (int i = 0; i < characters.size(); i++) {
            CharacterRecord character = characters.get(i);

            if (character.id() == id) {
                characters.remove(i);
                writeAll(characters);
                return true;
            }
        }

        return false;
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

    private String toCsvLine(CharacterRecord character) {
        return String.join(
                ",",
                Long.toString(character.id()),
                character.name(),
                character.status(),
                character.species(),
                character.type(),
                character.gender(),
                character.originName(),
                character.locationName(),
                character.created()
        );
    }

    private void writeAll(List<CharacterRecord> characters) {
        try (BufferedWriter writer = Files.newBufferedWriter(csvFile, StandardCharsets.UTF_8)) {
            writer.write(CSV_HEADER);

            for (CharacterRecord character : characters) {
                writer.newLine();
                writer.write(toCsvLine(character));
            }
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Can't write file by path: " + csvFile,
                    exception
            );
        }
    }
}
