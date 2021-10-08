package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        // Inner class to count total calories per day and excess

        List<UserMeal> userMealList = new ArrayList<>();
        Map<LocalDate, Integer> totalCal = new HashMap<>();
        // Get the UserMeal list according to startTime and endTime
        // Get the totalCaloriesPerDayMap Map with total calories per day and excess variable
        for (UserMeal userMeal : meals) {

            int start = userMeal.getDateTime().compareTo(startTime.atDate(userMeal.getDateTime().toLocalDate()));
            int end = userMeal.getDateTime().compareTo(endTime.atDate(userMeal.getDateTime().toLocalDate()));
            if (start >= 0 && end <= 0) {
                userMealList.add(userMeal);
            }

            totalCal.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (x, amount) -> x += amount);
        }

        // Fill the List<UserMealWithExcess>
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        userMealList.forEach(x -> userMealWithExcesses.add(new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(), totalCal.get(x.getDateTime().toLocalDate()) > caloriesPerDay)));

        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        Map<LocalDate, Integer> totalCaloriesPerDayMap = meals.stream()
                .collect(Collectors.groupingBy(u -> u.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return  meals.stream()
                .filter(x -> x.getDateTime().compareTo(startTime.atDate(x.getDateTime().toLocalDate())) >= 0
                        && x.getDateTime().compareTo(endTime.atDate(x.getDateTime().toLocalDate())) <= 0)
                .map(x -> new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(), totalCaloriesPerDayMap.get(x.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
