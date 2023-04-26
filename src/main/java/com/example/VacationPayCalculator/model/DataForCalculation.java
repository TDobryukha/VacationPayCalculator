package com.example.VacationPayCalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Data
public class DataForCalculation {
    @Positive
    private double avgSalary;
    @Positive
    @Max(365)
    private int amountOfDays;
}
