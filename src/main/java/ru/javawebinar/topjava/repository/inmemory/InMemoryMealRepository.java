package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new HashMap<>();
    private Integer counter = 0;

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(++counter);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meal.getUserId().equals(MealsUtil.CURRENT_USER) ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id) {
        return  repository.get(id).getUserId().equals(MealsUtil.CURRENT_USER) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return repository.getOrDefault(id, null);
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> meals = new ArrayList<>(repository.values());
        return meals.stream().filter(n -> n.getUserId().equals(MealsUtil.CURRENT_USER)).sorted(Meal.COMPARE_BY_TIME).collect(Collectors.toList());
    }
}

