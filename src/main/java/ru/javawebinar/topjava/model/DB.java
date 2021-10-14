package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicInteger;

public class DB {
    private static AtomicInteger id = new AtomicInteger();

    public static long getId() {
        return id.getAndAdd(1);
    }

}
