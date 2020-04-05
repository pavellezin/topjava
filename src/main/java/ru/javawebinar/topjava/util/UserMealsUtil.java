package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(12, 0), 2000));

    }

    private static boolean isExcess(int caloriesLimit, Map<LocalDate, Integer> dailyCalories, UserMeal meal) {
        return dailyCalories.get(meal.getDate()) > caloriesLimit;
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Filling total calories per day to Map dailyCalories using forEach
        Map<LocalDate, Integer> dailyCalories = new HashMap<>();
        for (UserMeal meal : meals) {
            dailyCalories.merge(meal.getDate(), meal.getCalories(), Integer::sum);
        }
        // Build filtered list of UserMealWithExcess using forEach
        List<UserMealWithExcess> resultList = new ArrayList<>();
        for (UserMeal meal : meals) {
            boolean excess = false;
            if ((dailyCalories.get(meal.getDateTime().toLocalDate())) > caloriesPerDay) excess = true;
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess));
            }
        }
        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Filling total calories per day to Map dailyCalories using Stream
        Map<LocalDate, Integer> dailyCalories = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        // Build filtered list of UserMealWithExcess using stream
        List<UserMealWithExcess> resultList = meals.stream()
                .filter(a -> (a.getDateTime().toLocalTime().equals(startTime)
                        || a.getDateTime().toLocalTime().isAfter(startTime))
                        && a.getDateTime().toLocalTime().isBefore(endTime))
                .map(meal -> new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        isExcess(caloriesPerDay, dailyCalories, meal)))
                .collect(Collectors.toList());
        return resultList;
    }
}
