package ru.javawebinar.topjava.repository.jpa;

import org.hibernate.type.descriptor.java.LocalDateJavaDescriptor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {


    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.getReference(User.class, userId);
        if (meal.isNew()) {
            System.out.println("save new user");
            meal.setUser(user);
            em.persist(meal);
            return meal;
        } else {
            System.out.println("merge");
            Meal dbMeal = em.find(Meal.class, meal.getId());
            if (dbMeal.getUser().getId() == user.getId()) {
                meal.setUser(user);
                return em.merge(meal);
            } else {
                return null;
            }

        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User user = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user", user)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = em.createNamedQuery(Meal.GET)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        if (startDateTime == null) {
            startDateTime = LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0);
        }
        if (endDateTime == null) {
            endDateTime = LocalDateTime.of(2050, Month.DECEMBER, 31, 23, 59);
        }
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("user_id", userId)
                .setParameter("start_time", startDateTime)
                .setParameter("end_time", endDateTime)
                .getResultList();
    }
}