package ru.kolosov.lab1.model;

public record CharacterRecord (
        long id,
        String name,
        String status,
        String species,
        String type,
        String gender,
        String originName,
        String locationName,
        String created
) {
}
