package com.vidmob.calculator.service;

import static com.vidmob.calculator.utils.TestUtils.EXCEPTION_MESSAGE;
import static com.vidmob.calculator.utils.TestUtils.MATH_PROBLEM;
import static com.vidmob.calculator.utils.TestUtils.RESULT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vidmob.calculator.calculator.BasicCalculator;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

    @InjectMocks
    private CalculationService service;

    @Mock
    private BasicCalculator calculator;

    @Test
    void calculate_CallsBasicCalculatorCalculate() {
        when(calculator.calculate(MATH_PROBLEM)).thenReturn(RESULT);

        service.calculate(MATH_PROBLEM);

        verify(calculator).calculate(MATH_PROBLEM);
    }

    @Test
    void calculate_WhenCalculatorThrowsIllegalArgumentException_ThrowsException() {
        when(calculator.calculate(MATH_PROBLEM)).thenThrow(new IllegalArgumentException(EXCEPTION_MESSAGE));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> service.calculate(MATH_PROBLEM));
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());

        verify(calculator).calculate(MATH_PROBLEM);
    }
}