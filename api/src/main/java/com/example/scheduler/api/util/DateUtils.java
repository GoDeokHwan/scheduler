package com.example.scheduler.api.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String yyyyMMdd = "yyyyMMdd";
    public static String HHmmss = "HHmmss";
    public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

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

    public static String yyyyMMdd(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(yyyyMMdd));
    }

    public static LocalDate asLocalDateYyyyMmDd(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String toTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern(HHmmss));
    }

    public static LocalTime newAsTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(HHmmss));
    }

    public static String toYyyyMMddHHmmss(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(yyyyMMddHHmmss));
    }

    public static LocalDateTime asYyyyMMddHHmmss(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(yyyyMMddHHmmss));
    }
}
