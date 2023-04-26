package com.example.VacationPayCalculator.service;

import com.example.VacationPayCalculator.model.DataForCalculation;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public double calculate(DataForCalculation data) throws IllegalArgumentException {
        if (data.getAmountOfDays() < 0 || data.getAvgSalary() < 0) {
            throw new IllegalArgumentException("Avg salary and amount of days must be positive");
        }
        return data.getAvgSalary() * data.getAmountOfDays();
    }
}
