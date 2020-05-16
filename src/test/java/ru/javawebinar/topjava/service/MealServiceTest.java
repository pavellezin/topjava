package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealTestData;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.web.meal.MealTestData.*;
import static ru.javawebinar.topjava.web.user.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.web.user.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml"
        , "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void get() {
        Meal actual = service.get(USER_MEAL_ID + 5, USER_ID);
        MEAL_MATCHER.assertMatch(actual, USER_MEAL6);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(ADMIN_ID + 100, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() throws Exception {
        service.delete(USER_MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(ADMIN_MEAL_ID + 100, ADMIN_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        LocalDate now = LocalDate.now();
        List<Meal> filtered = service.getBetweenHalfOpen(now.plus(1, ChronoUnit.YEARS), now, USER_ID);
        Assert.assertEquals(0, filtered.size());
        filtered = service.getBetweenHalfOpen(now.minus(1, ChronoUnit.YEARS), now.minus(1, ChronoUnit.MONTHS), USER_ID);
        Assert.assertEquals(7, filtered.size());
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        Assert.assertEquals(7, all.size());
        MEAL_MATCHER.assertMatch(all, MEALS);
    }

    @Test
    public void update() throws Exception {
        Meal created = MealTestData.getNew();
        service.create(created, USER_ID);
        Integer createdId = created.getId();
        MealTestData.getUpdated(created);
        service.update(created, USER_ID);
        Meal updated = service.get(createdId, USER_ID);
        MEAL_MATCHER.assertMatch(updated, created);
    }

    @Test(expected = NotFoundException.class)
    public void updatedNotFound() throws Exception {
        service.update(USER_MEAL3, ADMIN_ID);
    }
}