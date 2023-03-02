package com.softweb.api.store.utils;

public class StringUtil {
    public static boolean isBlankString (String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }
}
