package ru.kolosov.lab1;

import ru.kolosov.lab1.reader.CharacterTypeReader;
import ru.kolosov.lab1.writer.TypeFileWriter;

import java.io.IOException;
import java.nio.file.Path;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws IOException{
        Path inputFile = Path.of("lab-1/src/main/resources/characters.csv");
        Path outputFile = Path.of("lab-1/types.txt");

        CharacterTypeReader reader = new CharacterTypeReader();
        TypeFileWriter writer = new TypeFileWriter();

        HashSet<String> types = reader.readTypes(inputFile);
        writer.writeTypes(outputFile, types);
    }
}
