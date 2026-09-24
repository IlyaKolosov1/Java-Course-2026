package ru.kolosov.lab1.writer;

import java.util.Set;

public class TypeConsoleWriter {

    public void writeTypes(Set<String> types) {
        for (String type : types) {
            System.out.println(type);
        }
    }
}
