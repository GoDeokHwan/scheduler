package com.example.scheduler.api.util;

public class StringUtils {

    public static boolean isInteger(String val) {
        try {
            Integer.valueOf(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
