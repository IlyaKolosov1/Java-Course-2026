package ru.kolosov.lab2.repository;

import ru.kolosov.lab2.model.ItemEntity;

import java.util.List;

public interface ItemRepository {
    int save(ItemEntity item);
    ItemEntity findById(int id);
    ItemEntity findByName(String name);
    List<ItemEntity> findAll();
    boolean update(ItemEntity item);
    void deleteById(int id);
}
