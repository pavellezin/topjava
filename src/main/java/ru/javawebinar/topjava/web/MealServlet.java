package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static String LIST_MEAL = "/meals.jsp";
    private MealServiceImpl dao;

    public MealServlet() {
        super();
        dao = new MealServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");

        String INSERT_OR_EDIT = "/meal.jsp";
        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(dao.getById(id));
            forward = LIST_MEAL;
            request.setAttribute("meals", MealsUtil.filteredByStreams(dao.allMeals(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = dao.getById(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeals")) {
            forward = LIST_MEAL;
            request.setAttribute("meals", MealsUtil.filteredByStreams(dao.allMeals(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else {
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
        try {
            Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(request.getParameter("dateTime"));
            meal.setDateTime(dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            dao.edit(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        request.setAttribute("meals", MealsUtil.filteredByStreams(dao.allMeals(), LocalTime.MIN, LocalTime.MAX, 2000));
        log.debug(">>Redirect from Post");
        view.forward(request, response);
    }
}
