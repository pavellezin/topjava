package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealServiceImpl implements MealService {
    private MealDAO mealDAO = new MealDAOImpl();

    @Override
    public List<Meal> allMeals() {
        return mealDAO.allMeals();
    }

    @Override
    public void add(Meal meal) {
        mealDAO.add(meal);
    }

    @Override
    public void delete(Meal meal) {
        mealDAO.delete(meal);
    }

    @Override
    public void edit(Meal meal) {
        mealDAO.edit(meal);
    }

    @Override
    public Meal getById(int id) {
        return mealDAO.getById(id);
    }
}
