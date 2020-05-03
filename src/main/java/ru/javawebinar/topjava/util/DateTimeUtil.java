package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 1, 1);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(2100, 12, 31, 23, 59);

    public static LocalDateTime getStartInclusive(LocalDate ld) {
        return ld != null ? ld.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime getEndExclusive(LocalDate ld) {
        return ld != null ? ld.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

