package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.TestMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 9;

    public static final Meal USER_MEAL1 = new Meal(USER_MEAL_ID, of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL2 = new Meal(USER_MEAL_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL3 = new Meal(USER_MEAL_ID + 2, of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL4 = new Meal(USER_MEAL_ID + 3, of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal USER_MEAL5 = new Meal(USER_MEAL_ID + 4, of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL6 = new Meal(USER_MEAL_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL7 = new Meal(USER_MEAL_ID + 6, of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510);
    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL_ID, of(2020, Month.JANUARY, 31, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL_ID + 1, of(2020, Month.JANUARY, 31, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> MEALS = Arrays.asList(USER_MEAL7, USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now().plus(2, ChronoUnit.DAYS), "New meal", 555);
    }

    public static Meal getUpdated(Meal meal) {
        meal.setDescription("updated meal");
        return meal;
    }

    public static TestMatcher<Meal> MEAL_MATCHER = TestMatcher.of();

}
