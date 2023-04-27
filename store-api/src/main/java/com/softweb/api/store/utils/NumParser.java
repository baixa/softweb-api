package com.softweb.api.store.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Util class that perform parsing data
 */
public class NumParser {

    /**
     * Parse string value of number to Integer value
     *
     * @param value Array of parsable data
     * @return List of parsed values
     */
    public static List<Integer> parseIntOrNull(String... value) {
        List<Integer> result = new ArrayList<>(value.length);
        try {
            Arrays.stream(value).mapToInt(Integer::parseInt).forEach(result::add);
        } catch (NumberFormatException e) {
            return null;
        }
        return result;
    }

    /**
     * Parse data value to Integer array
     *
     * @param localDateTime Parsable datetime
     * @return Parsed datetime
     */
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
