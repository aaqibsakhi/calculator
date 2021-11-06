package com.vidmob.calculator.integrationtests;

import static com.jayway.restassured.RestAssured.given;
import static com.vidmob.calculator.utils.TestUtils.EXCEPTION_MESSAGE;
import static com.vidmob.calculator.utils.TestUtils.MATH_PROBLEM;
import static com.vidmob.calculator.utils.TestUtils.RESULT;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vidmob.calculator.service.CalculationService;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class CalculationControllerIT {

    private static final String GET_CALCULATION_URL = "/calculator/calculate/{mathProblem}";

    @LocalServerPort
    private int port;

    @MockBean
    private CalculationService calculationService;

    @Test
    void success_shouldReturnExpectedValue() {
        when(calculationService.calculate(MATH_PROBLEM)).thenReturn(RESULT);

        given().port(port)
                .when()
                .get(GET_CALCULATION_URL, MATH_PROBLEM)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(is(RESULT));
    }

    @Test
    void fail_shouldReturnBadRequest() {
        when(calculationService.calculate(MATH_PROBLEM)).thenThrow(new IllegalArgumentException(EXCEPTION_MESSAGE));

        given().port(port)
                .when()
                .get(GET_CALCULATION_URL, MATH_PROBLEM)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is(EXCEPTION_MESSAGE));
    }
}
