package com.example.VacationPayCalculator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Data
public class DataForCalculation {
    @Positive
    double avgSalary;
    @Positive
    @Max(365)
    int amountOfDays;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "[HH:mm][H:mm]")
}
