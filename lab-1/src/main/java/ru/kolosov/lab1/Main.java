package ru.kolosov.lab1;

import ru.kolosov.lab1.model.CharacterRecord;
import ru.kolosov.lab1.reader.CharacterTypeReader;
import ru.kolosov.lab1.repository.CharacterCsvRepository;
import ru.kolosov.lab1.writer.TypeFileWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;

public class Main {

    // private static final long TEST_CHARACTER_ID = 1000;

    public static void main(String[] args) throws IOException {
        Path inputFile = Path.of(
                "lab-1/src/main/resources/characters.csv"
        );

        Path outputFile = Path.of("lab-1/types.txt");

        CharacterTypeReader reader = new CharacterTypeReader();
        TypeFileWriter writer = new TypeFileWriter();

        HashSet<String> types = reader.readTypes(inputFile);

        writer.writeTypes(outputFile, types);
        writer.writeTypesInConsole(types);

        // CharacterCsvRepository repository =
        //         new CharacterCsvRepository(inputFile);

        // demonstrateCrud(repository);
    }

        // private static void demonstrateCrud(
        //         CharacterCsvRepository repository
        // ) throws IOException {

        //     CharacterRecord newCharacter = new CharacterRecord(
        //             TEST_CHARACTER_ID,
        //             "Test Rick",
        //             "Alive",
        //             "Human",
        //             "Test type",
        //             "Male",
        //             "Earth",
        //             "Citadel of Ricks",
        //             "2026-09-24T00:00:00.000Z"
        //     );

        //     repository.create(newCharacter);

        //     System.out.println("\nCreated:");
        //     System.out.println(
        //             repository.findById(TEST_CHARACTER_ID)
        //     );

        //     CharacterRecord updatedCharacter = new CharacterRecord(
        //             TEST_CHARACTER_ID,
        //             "Updated Rick",
        //             "Dead",
        //             "Human",
        //             "Updated type",
        //             "Male",
        //             "Earth",
        //             "Citadel of Ricks",
        //             "2026-09-24T00:00:00.000Z"
        //     );

        //     boolean updated = repository.update(updatedCharacter);

        //     System.out.println("\nUpdated: " + updated);
        //     System.out.println(
        //             repository.findById(TEST_CHARACTER_ID)
        //     );

        //     boolean deleted = repository.deleteById(TEST_CHARACTER_ID);

        //     System.out.println("\nDeleted: " + deleted);
        //     System.out.println(
        //             repository.findById(TEST_CHARACTER_ID)
        //     );
        // }
}