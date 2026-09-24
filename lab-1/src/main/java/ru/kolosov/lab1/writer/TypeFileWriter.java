package ru.kolosov.lab1.writer;

import java.nio.file.Path; // Тип данных 
import java.util.HashSet; // Коллекция
import java.util.Set;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class TypeFileWriter {

    public void writeTypes(Path outputFile, Set<String> types){
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {

            for (String type: types) {
                writer.write(type);
                writer.newLine();
            }

        } catch (IOException exception) {
            throw new RuntimeException(
                "Can't write in file by path: " + outputFile,
                exception
            );
        }
    }

    public void  writeTypesInConsole(Set<String> types){
        try {
            for (String type : types){
                System.out.printf(type + '\n');
            }
        } catch (Exception exception) {
            throw new RuntimeException(
                "Can't write in console by path: ",
                exception
            );

        }
    }
    
}
