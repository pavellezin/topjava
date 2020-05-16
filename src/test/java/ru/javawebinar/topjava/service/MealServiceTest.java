package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.meal.MealTestData;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.web.meal.MealTestData.*;
import static ru.javawebinar.topjava.web.user.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml"
        , "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    private static ConfigurableApplicationContext appCtx;
    private static MealRestController controller;

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MealTestData.assertMealEquals(created, newMeal);
        MealTestData.assertMealEquals(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void get() {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer createdId = created.getId();
        Meal actual = service.get(createdId, USER_ID);
        MealTestData.assertMealEquals(created, actual);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        Meal newMeal = MealTestData.getNew();
        Meal adminMeal = service.create(newMeal, ADMIN_ID);
        Integer adminMealId = adminMeal.getId();
        service.get(adminMealId + 100, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() throws Exception {
        Meal userMeal = service.create(USER_MEAL, USER_ID);
        Integer userMealId = userMeal.getId();
        service.delete(userMealId, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        Meal newMeal = MealTestData.getNew();
        Meal adminMeal = service.create(newMeal, ADMIN_ID);
        Integer adminMealId = adminMeal.getId();
        service.delete(adminMealId + 100, ADMIN_ID);
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
        List<Meal> all = service.getAll(ADMIN_ID);
        Assert.assertEquals(2, all.size());
        Meal adminMeal = service.create(ADMIN_MEAL, ADMIN_ID);
        Integer id = adminMeal.getId();
        all = service.getAll(ADMIN_ID);
        Assert.assertEquals(3, all.size());
        service.delete(id, ADMIN_ID);
        all = service.getAll(ADMIN_ID);
        Assert.assertEquals(2, all.size());
    }

    @Test
    public void update() throws Exception {
        Meal created = service.create(USER_MEAL, USER_ID);
        Integer createdId = created.getId();
        MealTestData.getUpdated(created);
        service.update(created, USER_ID);
        Meal updated = service.get(createdId, USER_ID);
        MealTestData.assertMealEquals(updated, created);
    }

    @Test(expected = NotFoundException.class)
    public void updatedNotFound() throws Exception {
        Meal created = service.create(USER_MEAL, USER_ID);
        MealTestData.getUpdated(created);
        service.update(created, ADMIN_ID);
    }
}