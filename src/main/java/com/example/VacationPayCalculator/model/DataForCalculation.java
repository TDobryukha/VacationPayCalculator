package com.example.VacationPayCalculator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DataForCalculation {
    @Positive
    private double avgSalary;
    @Max(365)
    private int amountOfDays;
    private LocalDate beginDate;
    private LocalDate endDate;
}
