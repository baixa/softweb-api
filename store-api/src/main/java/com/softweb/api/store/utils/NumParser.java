package com.softweb.api.store.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumParser {
    public static List<Integer> parseIntOrNull(String... value) {
        List<Integer> result = new ArrayList<>(value.length);
        try {
            Arrays.stream(value).mapToInt(Integer::parseInt).forEach(result::add);
        } catch (NumberFormatException e) {
            return null;
        }
        return result;
    }
    
    public static int[] parseDateToNumArray(LocalDateTime localDateTime) {
        int[] parsedDate = new int[5];
        parsedDate[0] = localDateTime.getYear();
        parsedDate[1] = localDateTime.getMonthValue();
        parsedDate[2] = localDateTime.getDayOfMonth();
        parsedDate[3] = localDateTime.getHour();
        parsedDate[4] = localDateTime.getMinute();
        return parsedDate;
    }
}
