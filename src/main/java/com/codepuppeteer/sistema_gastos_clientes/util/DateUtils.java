package com.codepuppeteer.sistema_gastos_clientes.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    public static String format(LocalDate date) {
        if (date == null) return null;
        return date.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static LocalDate parse(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    public static LocalDate now() {
        return LocalDate.now();
    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }
}
