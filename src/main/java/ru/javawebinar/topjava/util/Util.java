package ru.javawebinar.topjava.util;

public class Util {
    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, T start, T end) {
        return (start == null ? true : value.compareTo(start) >= 0) && (end == null ? true : value.compareTo(end) < 0);
    }

}
