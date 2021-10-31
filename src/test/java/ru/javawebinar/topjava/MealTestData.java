package ru.javawebinar.topjava;

import org.junit.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    //public static final int MEAL_ID1 = START_SEQ;
    public static final int MEAL_ID1 = 100_002;
    //public static final int MEAL_ID2 = START_SEQ + 1;
    public static final int MEAL_ID2 = MEAL_ID1 + 7;
    public static final int NOT_FOUND = 10;

    public static final Meal meal1 = new Meal(MEAL_ID1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL_ID2, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2018, Month.JANUARY, 30, 10, 0), "Завтрак2", 5000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDescription("update Завтрак");
        updated.setCalories(3000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        //assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
        Assert.assertEquals(actual, expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        //assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
        assertMatch(actual, expected);
    }
}
