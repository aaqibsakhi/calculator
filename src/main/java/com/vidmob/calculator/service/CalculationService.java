package com.vidmob.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidmob.calculator.calculator.BasicCalculator;

@Service
public class CalculationService {

    private final BasicCalculator calculator;

    @Autowired
    public CalculationService(BasicCalculator calculator) {
        this.calculator = calculator;
    }

    public String calculate(String mathProblem) {
        return calculator.calculate(mathProblem);
    }
}
