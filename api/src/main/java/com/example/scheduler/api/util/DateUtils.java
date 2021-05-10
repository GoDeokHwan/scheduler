package com.example.scheduler.api.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateUtils {
    public static boolean isDate(int year, int month, int day) {
        try {
            if (year >= 1900) { // 최소 1900년 이상부터 측정
                LocalDate localDate = LocalDate.of(year, month, day);
            } else {
                return false;
            }
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    public static boolean isTime(int hour, int min) {
        try {
            LocalTime localDate = LocalTime.of(hour, min);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
