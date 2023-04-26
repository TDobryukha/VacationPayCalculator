package com.example.VacationPayCalculator.service;

import com.example.VacationPayCalculator.model.DataForCalculation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {
    private static CalculatorService service = new CalculatorService();

    @ParameterizedTest
    @CsvSource({"10000, 5, 50000",
            "10500.22, 5, 52501.1",
            "0, 5, 0",
            "1000, 0, 0",
            "0, 0, 0",
            "1.7976931348623157E308, 1, 1.7976931348623157E308",
            "1.7976931348623157E308, 2, Infinity",

    })
    void testCalculate(double avgSalary, int amountOfDays, double result) {
        assertEquals(result, service.calculate(new DataForCalculation(avgSalary, amountOfDays)));
    }

    @ParameterizedTest
    @CsvSource({"-1, 5",
            "10, -1",
            "-1, -1"
    })
    void testIllegalArgumentException(double avgSalary, int amountOfDays) {
        assertThrows(IllegalArgumentException.class, () -> service.calculate(new DataForCalculation(avgSalary, amountOfDays)));
    }
}