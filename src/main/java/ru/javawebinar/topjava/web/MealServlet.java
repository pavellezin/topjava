package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.DemoMealList;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug(">>Redirect to meals");
        List<MealTo> mealToList = MealsUtil.filteredByStreams(DemoMealList.DEMO_MEALS, LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("meals",
                mealToList);
        request.setAttribute("size",
                DemoMealList.DEMO_MEALS_SIZE);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
