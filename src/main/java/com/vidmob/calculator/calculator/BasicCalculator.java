package com.vidmob.calculator.calculator;

import static java.lang.Double.parseDouble;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class BasicCalculator {

    public String calculate(String mathProblem) {
        mathProblem = StringUtils.deleteWhitespace(mathProblem);
        validate(mathProblem);

        List<String> operationList = createOperationList(mathProblem);
        runMathOperation(operationList, "/");
        runMathOperation(operationList, "*");
        runMathOperation(operationList, "+");
        runMathOperation(operationList, "-");

        return operationList.get(0);
    }

    private ArrayList<String> createOperationList(String mathProblem) {
        ArrayList<String> operationList = new ArrayList<>();
        // Regex with positive lookbehind to see if there is a -+*+ preceding a positive or negative integer
        // Main regex function identifies one or more integers with optional decimal point
        // OR function matches one character from -+/* if integer is not matched
        Pattern pattern = Pattern.compile("(?:(?<=[-+/*]|^)[-+])?\\.?\\d+|[-+/*]");
        Matcher matcher = pattern.matcher(mathProblem);
        while (matcher.find()) {
            operationList.add(matcher.group());
        }
        return operationList;
    }

    private void runMathOperation(List<String> operationList, String operation) {
        for (int i = 0; i < operationList.size(); i++) {
            double result;
            if (operationList.get(i).equals(operation)) {
                switch (operation) {
                case "/":
                    result = parseDouble(operationList.get(i - 1)) / parseDouble(operationList.get(i + 1));
                    break;
                case "*":
                    result = parseDouble(operationList.get(i - 1)) * parseDouble(operationList.get(i + 1));
                    break;
                case "+":
                    result = parseDouble(operationList.get(i - 1)) + parseDouble(operationList.get(i + 1));
                    break;
                case "-":
                    result = parseDouble(operationList.get(i - 1)) - parseDouble(operationList.get(i + 1));
                    break;
                default:
                    result = 0;
                    break;
                }
                operationList.set(i, Double.toString(result));
                operationList.remove(i + 1);
                operationList.remove(i - 1);
                // Need better solution to restart loop for identical math operators
                i--;
            }
        }
    }

    private void validate(String mathProblem) {
        checkForTooManyOperators(mathProblem);
        checkForNonNumericValues(mathProblem);
    }

    private void checkForNonNumericValues(String mathProblem) {
        mathProblem = StringUtils.replace(mathProblem, "/", StringUtils.EMPTY);
        mathProblem = StringUtils.replace(mathProblem, "*", StringUtils.EMPTY);
        mathProblem = StringUtils.replace(mathProblem, "+", StringUtils.EMPTY);
        mathProblem = StringUtils.replace(mathProblem, "-", StringUtils.EMPTY);
        mathProblem = StringUtils.replace(mathProblem, ".", StringUtils.EMPTY);
        if (!StringUtils.isNumeric(mathProblem)) {
            throw new IllegalArgumentException("invalid input");
        }
    }

    private void checkForTooManyOperators(String mathProblem) {
        Pattern pattern = Pattern.compile("[+-/*]{3,}");
        Matcher matcher = pattern.matcher(mathProblem);
        if (matcher.find()) {
            throw new IllegalArgumentException("invalid operator combination - too many consecutive operators");
        }
    }
}