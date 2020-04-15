package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MemoryDAO implements DAO {
    private AtomicInteger AUTO_ID = new AtomicInteger(0);
    private Map<Integer, Meal> dao = new ConcurrentHashMap<>();

    {
        add(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        add(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> all() {
        return new ArrayList<>(dao.values());
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(AUTO_ID.incrementAndGet());
        return dao.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        dao.remove(id);
    }

    @Override
    public void edit(Meal meal) {
        dao.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        return dao.get(id);
    }
}
