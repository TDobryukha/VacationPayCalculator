package com.example.VacationPayCalculator.controller;

import com.example.VacationPayCalculator.model.DataForCalculation;
import com.example.VacationPayCalculator.service.CalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CalculatorController {
    private final CalculatorService service;

    public CalculatorController(CalculatorService service) {
        this.service = service;
    }

    @GetMapping("calculate")
    public double calculate(@Valid @ModelAttribute DataForCalculation data) {
        return service.calculate(data);
    }
}
