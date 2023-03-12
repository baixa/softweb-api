package com.softweb.api.store.utils;

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
}
