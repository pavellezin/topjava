package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DAO;
import ru.javawebinar.topjava.dao.MemoryDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Dates;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static String LIST_MEAL = "/meals.jsp";
    private DAO dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new MemoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action").toLowerCase();
        String INSERT_OR_EDIT = "/meal.jsp";
        int id;

        switch (action) {
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                log.debug(">>Delete item id=" + id);
                forward = LIST_MEAL;
                request.setAttribute("meals", MealsUtil.filteredByStreams(dao.all(), LocalTime.MIN, LocalTime.MAX, 2000));
                break;
            case "edit":
                forward = INSERT_OR_EDIT;
                id = Integer.parseInt(request.getParameter("id"));
                Meal meal = dao.getById(id);
                request.setAttribute("meal", meal);
                break;
            case "listmeals":
                forward = LIST_MEAL;
                log.debug(">>List all items");
                request.setAttribute("meals", MealsUtil.filteredByStreams(dao.all(), LocalTime.MIN, LocalTime.MAX, 2000));
                break;
            default:
                forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        log.debug(">>Redirect from Get, forward=" + forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime"), Dates.MEAL_FORMATTER));
        log.debug(">>meal=" + meal);

        String mealId = request.getParameter("id");
        log.debug(">>meal id=" + mealId);
        if (mealId == null || mealId.isEmpty()) {
            dao.add(meal);
            log.debug(">>Add new item, id=" + mealId);
        } else {
            meal.setId(Integer.parseInt(mealId));
            dao.edit(meal);
            log.debug(">>Edit item id=" + mealId);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        request.setAttribute("meals", MealsUtil.filteredByStreams(dao.all(), LocalTime.MIN, LocalTime.MAX, 2000));
        log.debug(">>Redirect from Post");
        view.forward(request, response);
    }
}
