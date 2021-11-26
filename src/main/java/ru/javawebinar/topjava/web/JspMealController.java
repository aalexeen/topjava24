package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    private MealService service;

    @GetMapping("")
    public String getMeal(Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        int mealId = 0;
        int userId = SecurityUtil.authUserId();
        switch (action == null ? "all" : action) {
            case "create", "update" -> {
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        service.get(Integer.parseInt(request.getParameter("id")), userId);
                model.addAttribute("meal", meal);
                return "mealForm";
            }
            case "delete" -> {
                mealId = Integer.parseInt(request.getParameter("id"));
                service.delete(mealId, userId);
                model.addAttribute("meals",
                        MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                return "meals";
            }
            case "filter" -> {
                LocalDate startDateTime = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDateTime = parseLocalDate(request.getParameter("endDate"));
                model.addAttribute("meals",
                        MealsUtil.getTos(service.getBetweenInclusive(startDateTime, endDateTime, userId),
                                MealsUtil.DEFAULT_CALORIES_PER_DAY));
                return "meals";
            }
            default -> {
                model.addAttribute("meals",
                        MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                return "meals";
            }
        }
    }

    @PostMapping("")
    public String setMeal(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        int userId = SecurityUtil.authUserId();

        if (StringUtils.hasLength(request.getParameter("id"))) {
            Meal meal = service.get(Integer.parseInt(request.getParameter("id")), userId);

            String description = request.getParameter("description");
            if (description != null) {
                meal.setDescription(description);
            }

            String calories = request.getParameter("calories");
            if (calories != null) {
                meal.setCalories(Integer.parseInt(calories));
            }

            String dateTime = request.getParameter("dateTime");
            if (dateTime != null) {
                meal.setDateTime(LocalDateTime.parse(dateTime));
            }

            service.update(meal, userId);
        } else {
            Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))
            );
            service.create(meal, userId);
        }

        return "redirect:meals";
    }
}
