package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dates {
    private Dates() {
    }

    public static final DateTimeFormatter MEAL_LOCAL_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(MEAL_LOCAL_DATE);
    }
}
