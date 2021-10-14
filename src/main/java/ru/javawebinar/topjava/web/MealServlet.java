package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int MAX_CALORIES = 2000;

    List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Redirect to meals");
        String forward = "";
        String action = req.getParameter("action");
        if (action != null) {
            if (action.equalsIgnoreCase("add")) {
                forward = "/meal.jsp";
            } else if (action.equalsIgnoreCase("delete")) {
                long mealId = Long.parseLong(req.getParameter("mealId"));
                meals = meals.stream().filter(x -> x.getId() != mealId).collect(Collectors.toList());
                forward = "/meals.jsp";
                req.setAttribute("mealList", MealsUtil.filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), MAX_CALORIES));
            } else if (action.equalsIgnoreCase("update")) {
                forward = "/meal.jsp";
                long mealId = Long.parseLong(req.getParameter("mealId"));
                Meal meal = meals.stream().filter(x -> x.getId() == mealId).findFirst().get();
                req.setAttribute("meal", meal);
            }
        } else {
            forward = "/meals.jsp";
            req.setAttribute("mealList", MealsUtil.filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), MAX_CALORIES));
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forward);
        dispatcher.forward(req, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("DateTime"), formatter);
        int mealId;
        if (req.getParameter("mealId") != null) {
            mealId = Integer.parseInt(req.getParameter("mealId"));
            meals = meals.stream()
                    .map(x -> x = (x.getId() == mealId) ? (new Meal(mealId, dateTime, req.getParameter("description"), Integer.parseInt(req.getParameter("calories")))) : x)
                    .collect(Collectors.toList());
        } else {
            Meal meal = new Meal(dateTime, req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
            meals = new ArrayList<>(meals);
            meals.add(meal);
        }


        req.setAttribute("mealList", MealsUtil.filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), MAX_CALORIES));
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/meals.jsp");
        dispatcher.forward(req, response);
    }
}
