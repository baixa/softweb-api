package com.softweb.api.store.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit-tests for NumParser class
 */
class NumParserTest {

    /**
     * Test for method parseIntOrNull(String... input) as default behavior
     */
    @Test
    void defaultBehavior() {
        String input = "3";
        List<Integer> expectedOutput = List.of(3);

        List<Integer> existsOutput = NumParser.parseIntOrNull(input);

        Assertions.assertNotNull(existsOutput);
        Assertions.assertEquals(expectedOutput.size(), existsOutput.size());
        Assertions.assertEquals(expectedOutput, existsOutput);
    }

    /**
     * Test for method parseIntOrNull(String... input) if string value is empty
     */
    @Test
    void emptyInput() {
        String input = "";
        List<Integer> existsOutput = NumParser.parseIntOrNull(input);

        Assertions.assertNull(existsOutput);
    }

    /**
     * Test for method parseIntOrNull(String... input) if input has several values
     */
    @Test
    void severalValues() {
        String[] input = new String[] {"3", "212", "547", "9999999"};
        List<Integer> expectedOutput = List.of(3, 212, 547, 9999999);

        List<Integer> existsOutput = NumParser.parseIntOrNull(input);

        Assertions.assertNotNull(existsOutput);
        Assertions.assertEquals(expectedOutput.size(), existsOutput.size());
        Assertions.assertEquals(expectedOutput, existsOutput);
    }

    /**
     * Test for method parseIntOrNull(String... input) if input has several values with incorrect elements
     */
    @Test
    void severalValuesWithIncorrectElements() {
        String[] input = new String[] {"3", "212", "547", "Non number"};
        List<Integer> existsOutput = NumParser.parseIntOrNull(input);

        Assertions.assertNull(existsOutput);
    }

    /**
     * Test for method parseIntOrNull(String... input) if input is positive infinity
     */
    @Test
    void positiveInfinity() {
        String input = String.valueOf(Double.POSITIVE_INFINITY);
        List<Integer> existsOutput = NumParser.parseIntOrNull(input);
        Assertions.assertNull(existsOutput);
    }

    /**
     * Test for method parseIntOrNull(String... input) if input is negative infinity
     */
    @Test
    void negativeInfinity() {
        String input = String.valueOf(Double.NEGATIVE_INFINITY);
        List<Integer> existsOutput = NumParser.parseIntOrNull(input);
        Assertions.assertNull(existsOutput);
    }

    /**
     * Test for method parseIntOrNull(String... input) if input is NaN
     */
    @Test
    void NaN() {
        String input = String.valueOf(Double.NaN);
        List<Integer> existsOutput = NumParser.parseIntOrNull(input);
        Assertions.assertNull(existsOutput);
    }

    /**
     * Test for method parseIntOrNull(String... input) if input is max INT value
     */
    @Test
    void maxValue() {
        String input = String.valueOf(Integer.MAX_VALUE);
        List<Integer> expectedOutput = List.of(Integer.MAX_VALUE);

        List<Integer> existsOutput = NumParser.parseIntOrNull(input);

        Assertions.assertNotNull(existsOutput);
        Assertions.assertEquals(expectedOutput.size(), existsOutput.size());
        Assertions.assertEquals(expectedOutput, existsOutput);
    }

    /**
     * Test for method parseIntOrNull(String... input) if input is more than max INT value
     */
    @Test
    void moreThanMaxValue() {
        Long moreThanMaxValue = (long) Integer.MAX_VALUE + 1;
        String input = String.valueOf(moreThanMaxValue);
        List<Integer> existsOutput = NumParser.parseIntOrNull(input);
        Assertions.assertNull(existsOutput);
    }

    @Test
    void parseDateToNumArray() {
    }
}