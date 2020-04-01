package ru.javawebinar.topjava.model;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserMeal implements Comparable<UserMeal>{
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {return dateTime.toLocalDate();}

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
    public int compareTo(UserMeal meal) {
        return dateTime.compareTo(meal.dateTime); // call String's compareTo
    }
}