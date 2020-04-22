package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository extends AbstractBaseEntity implements MealRepository {
    private Map<Integer, Meal> repository = new HashMap<>();
    private Integer counter = 0;

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    public InMemoryMealRepository(Integer id) {
        super(id);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew() && id.equals(meal.getUserId())) {
            meal.setId(++counter);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        Meal found = repository.get(id);
        ValidationUtil.checkNotFoundWithId(found, id);
        return found;
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> meals = Collections.list(Collections.enumeration(repository.values()));
        meals.sort(Meal::compareTo);
        return meals;

    }
}

