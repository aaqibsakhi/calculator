package com.vidmob.calculator.controller;

import static com.vidmob.calculator.utils.TestUtils.EXCEPTION_MESSAGE;
import static com.vidmob.calculator.utils.TestUtils.MATH_PROBLEM;
import static com.vidmob.calculator.utils.TestUtils.RESULT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vidmob.calculator.service.CalculationService;

@ExtendWith(MockitoExtension.class)
class CalculationControllerTest {

    @InjectMocks
    private CalculationController controller;

    @Mock
    private CalculationService service;

    @Test
    void calculate_WhenServiceIsCalledSuccessfully_ReturnsOkWithValue() {
        when(service.calculate(MATH_PROBLEM)).thenReturn(RESULT);

        assertEquals(new ResponseEntity<>(RESULT, HttpStatus.OK), controller.calculate(MATH_PROBLEM));

        verify(service).calculate(MATH_PROBLEM);
    }

    @Test
    void handleIllegalArgumentException_ReturnsBadRequest() {
        assertEquals(new ResponseEntity<>(EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST),
                controller.handleIllegalArgumentException(new IllegalArgumentException(EXCEPTION_MESSAGE)));
    }
}