package ru.javawebinar.topjava.web.meal;

import org.junit.ComparisonFailure;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class MealTestData {
    public static final Meal USER_MEAL = new Meal(null, LocalDateTime.now(), "User meal", 1000);
    public static final Meal ADMIN_MEAL = new Meal(null, LocalDateTime.now().plus(1, ChronoUnit.DAYS), "Admin meal", 1500);
    public static final Comparator<Meal> MEAL_COMPARATOR = Comparator
            .comparing(Meal::getDateTime)
            .thenComparing(Meal::getId)
            .thenComparing(Meal::getDescription)
            .thenComparing(Meal::getCalories);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now().plus(2, ChronoUnit.DAYS), "New meal", 555);
    }

    public static Meal getUpdated(Meal meal) {
        meal.setDescription("updated meal");
        return meal;
    }

    public static boolean testMealEqual(Meal expected, Meal actual) {
        return MEAL_COMPARATOR.compare(expected, actual) == 0;
    }

    public static void assertMealEquals(Meal expected, Meal actual) {
        if (!testMealEqual(expected, actual)) {
            throw new ComparisonFailure("Meals not equal", expected.getId().toString(), actual.getId().toString());
        }
    }

}
