package com.vidmob.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vidmob.calculator.service.CalculationService;

@RestController
public class CalculationController {

    private final CalculationService service;

    @Autowired
    public CalculationController(CalculationService service) {
        this.service = service;
    }

    @GetMapping("/calculate/{mathProblem}")
    public ResponseEntity<String> calculate(@PathVariable String mathProblem) {
        return new ResponseEntity<>(service.calculate(mathProblem), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
