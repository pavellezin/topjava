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

//      List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(12, 0), 2000);
//      mealsTo.forEach(System.out::println);

//      System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCalories = new TreeMap<>();
        List<UserMealWithExcess> resultList = new ArrayList<>();
        boolean excess;
        // Sorting meals by date using compareTo()
        Collections.sort(meals);
        // Filling total calories per day to Map dailyCalories using forEach
        for(UserMeal meal: meals){
            dailyCalories.computeIfPresent(meal.getDateTime().toLocalDate(), (k,v)->v+=meal.getCalories());
            if(!(dailyCalories.containsKey(meal.getDateTime().toLocalDate()))){
                dailyCalories.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            }
        }
        // Filter
        for(UserMeal meal: meals){
            if ((dailyCalories.get(meal.getDateTime().toLocalDate())) > caloriesPerDay) excess = true;
            else excess = false;
            if(((meal.getDateTime().toLocalTime().isAfter(startTime))||(meal.getDateTime().toLocalTime().equals(startTime)))&&(meal.getDateTime().toLocalTime().isBefore(endTime))){
                resultList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess));
            }
        }
        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> resultList = new ArrayList<>();
        boolean excess;
        // Filling total calories per day to Map dailyCalories using Stream
        Map<LocalDate, Integer> dailyCalories = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        // Filter
//      List<UserMealWithExcess> result =
        meals.stream()
                .filter(a->(a.getDateTime().toLocalTime().equals(startTime)
                        ||a.getDateTime().toLocalTime().isAfter(startTime))
                        &&a.getDateTime().toLocalTime().isBefore(endTime))
                .forEach(System.out::println);
        // TODO Implement by streams
        return null;
    }
}
