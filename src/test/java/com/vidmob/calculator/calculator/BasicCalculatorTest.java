package com.vidmob.calculator.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class BasicCalculatorTest {

    private BasicCalculator basicCalculator;

    @BeforeEach
    void setUp() {
        basicCalculator = new BasicCalculator();
    }

    @Test
    void calculate_WithSingleOperator_ReturnsCorrectAnswer() {
        assertEquals("3.0", basicCalculator.calculate("1 + 2"));
    }

    @Test
    void calculate_WithSingleOperatorNoSpaces_ReturnsCorrectAnswer() {
        assertEquals("3.0", basicCalculator.calculate("1+2"));
    }

    @Test
    void calculate_WithMultipleOperators_ReturnsCorrectAnswer() {
        assertEquals("10.0", basicCalculator.calculate("4 * 5 / 2"));
    }

    @Test
    void calculate_WithMultipleIdenticalOperators_ReturnsCorrectAnswer() {
        assertEquals("1.0", basicCalculator.calculate("10 / 5 / 2"));
    }

    @Test
    void calculate_WithMultipleOperatorsNoSpaces_ReturnsCorrectAnswer() {
        assertEquals("10.0", basicCalculator.calculate("4*5/2"));
        assertEquals("100.0", basicCalculator.calculate("100*5/2-50+100"));

    }

    @Test
    void calculate_WithMultipleOperatorsAndNegativeIntegers_ReturnsCorrectAnswer() {
        assertEquals("9.0", basicCalculator.calculate("-5+-8--11*2"));
    }

    @Test
    void calculate_WithMultipleOperatorsAndDecimals_ReturnsCorrectAnswer() {
        assertEquals("-0.64", basicCalculator.calculate("-.32/.5"));
    }

    @Test
    void calculate_WithMultipleOperatorsAndSpaces_ReturnsCorrectAnswer() {
        assertEquals("-0.64", basicCalculator.calculate("-.32     / .5"));
    }

    @Test
    void calculate_WithInvalidOperatorCombination_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> basicCalculator.calculate("2+-+-4"));
        assertEquals("invalid operator combination - too many consecutive operators", exception.getMessage());
    }

    @Test
    void calculate_WithInvalidCalculation_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> basicCalculator.calculate("19 + cinnamon"));
        assertEquals("invalid input", exception.getMessage());
    }

    // TODO
    @Disabled("Code not implemented")
    @Test
    void calculate_WithParentheses_ReturnsCorrectAnswer() {
        assertEquals("7.0", basicCalculator.calculate("(4-2)*3.5"));
    }
}