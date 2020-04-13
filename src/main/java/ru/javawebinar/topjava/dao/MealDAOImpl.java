package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAOImpl implements MealDAO {
    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    private static Map<Integer, Meal> meals = new HashMap<>();

    static {
        Meal meal1 = new Meal();
        meal1.setId(AUTO_ID.getAndIncrement());
        meal1.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0));
        meal1.setDescription("Завтрак");
        meal1.setCalories(500);
        meals.put(meal1.getId(), meal1);

        Meal meal2 = new Meal();
        meal2.setId(AUTO_ID.getAndIncrement());
        meal2.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0));
        meal2.setDescription("Обед");
        meal2.setCalories(1000);
        meals.put(meal2.getId(), meal2);

        Meal meal3 = new Meal();
        meal3.setId(AUTO_ID.getAndIncrement());
        meal3.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0));
        meal3.setDescription("Ужин");
        meal3.setCalories(500);
        meals.put(meal3.getId(), meal3);

        Meal meal4 = new Meal();
        meal4.setId(AUTO_ID.getAndIncrement());
        meal4.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0));
        meal4.setDescription("Еда на граничное значение");
        meal4.setCalories(100);
        meals.put(meal4.getId(), meal4);

        Meal meal5 = new Meal();
        meal5.setId(AUTO_ID.getAndIncrement());
        meal5.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0));
        meal5.setDescription("Завтрак");
        meal5.setCalories(1000);
        meals.put(meal5.getId(), meal5);

        Meal meal6 = new Meal();
        meal6.setId(AUTO_ID.getAndIncrement());
        meal6.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 31, 12, 0));
        meal6.setDescription("Обед");
        meal6.setCalories(500);
        meals.put(meal6.getId(), meal6);

        Meal meal7 = new Meal();
        meal7.setId(AUTO_ID.getAndIncrement());
        meal7.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0));
        meal7.setDescription("Ужин");
        meal7.setCalories(410);
        meals.put(meal7.getId(), meal7);

    }

    @Override
    public List<Meal> allMeals() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void add(Meal meal) {
        meal.setId(AUTO_ID.getAndIncrement());
        meals.put(meal.getId(), meal);
    }

    @Override
    public void delete(Meal meal) {
        meals.remove(meal.getId());
    }

    @Override
    public void edit(Meal meal) {
        meals.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }
}
