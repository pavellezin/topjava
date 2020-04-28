package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.*;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    public static final Comparator<Meal> COMPARE_BY_TIME = (l, r) -> r.getDateTime().compareTo(l.getDateTime());

    {
        MealsUtil.MEALS.forEach(m -> save(m, USER_ID));
        save(new Meal(LocalDateTime.of(2020, 04, 24, 8, 0), "Завтрак", 500), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2020, 04, 24, 12, 0), "Обед", 1500), ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals.get(id) != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals.equals(null) ? null : meals.getOrDefault(id, null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        List<Meal> mealsValues = new ArrayList<>(meals.values());
        return mealsValues.equals(null) ?
                Collections.emptyList() :
                mealsValues.stream()
                        .sorted(COMPARE_BY_TIME)
                        .collect(Collectors.toList());
    }

    @Override
    public List<Meal> isBetweenHalfOpen(LocalDateTime start, LocalDateTime end, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        List<Meal> mealsValues = new ArrayList<>(meals.values());
        return mealsValues.equals(null) ?
                Collections.emptyList() :
                mealsValues.stream()
                        .filter(m -> Util.isBetweenHalfOpen(m.getDateTime(), start, end))
                        .sorted(COMPARE_BY_TIME)
                        .collect(Collectors.toList());
    }

}

