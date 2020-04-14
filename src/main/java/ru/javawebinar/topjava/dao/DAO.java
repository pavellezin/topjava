package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface DAO {
    List<Meal> all();

    Meal add(Meal meal);

    void delete(int id);

    void edit(Meal meal);

    Meal getById(int id);
}
