package com.example.VacationPayCalculator.service;

import com.example.VacationPayCalculator.model.DataForCalculation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CalculatorServiceTest {

    private final CalculatorService service;

    @Autowired
    public CalculatorServiceTest(CalculatorService service) {
        this.service = service;
    }

    private static Stream<Arguments> createArgumentsForTestCalculateRangeOfDates() {
        return Stream.of(
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .amountOfDays(5)
                        .build(), 1706.4846416382252), // test ok
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10500.22)
                        .amountOfDays(5)
                        .build(), 1791.8464163822523), // float value avgSalary
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(1.7976931348623157E308)
                        .amountOfDays(30)
                        .build(), "Infinity"), // result is infinity
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(1.7976931348623157E308)
                        .amountOfDays(1)
                        .build(), 6.135471450042033E306), // one vacation day, amountOfDays is specified
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .beginDate(LocalDate.of(2023, 3, 7))
                        .endDate(LocalDate.of(2023, 3, 7))
                        .build(), 341.29692832764505), // one vacation day, vacation period  is specified
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .beginDate(LocalDate.of(2023, 4, 29))
                        .endDate(LocalDate.of(2023, 5, 2))
                        .build(), 341.29692832764505), //two vacation days, vacation period is specified
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .beginDate(LocalDate.of(2022, 3, 5)) //Sat 05.03.22 - work, 06.03 - Sun
                        .endDate(LocalDate.of(2022, 3, 9)) //07.03, 08.03 - holiday
                        .build(), 682.5938566552901), //shifting the working day, 2 holiday days, one sunday
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .beginDate(LocalDate.of(2023, 3, 5)) //Sun 05.03.23 - day off,
                        .endDate(LocalDate.of(2023, 3, 9))      //08.03 - holiday
                        .build(), 1023.8907849829352), //1 holiday day, one sunday
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .amountOfDays(5)
                        .beginDate(LocalDate.of(2023, 3, 7))
                        .endDate(LocalDate.of(2023, 3, 7))
                        .build(), 341.29692832764505), // "only period is taken for calculation, amountOfDays is calculated from period"
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .amountOfDays(5)
                        .endDate(LocalDate.of(2023, 3, 7))
                        .build(), 1706.4846416382252), //endDate is ignored because beginDate is not specified
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .amountOfDays(5)
                        .beginDate(LocalDate.of(2023, 3, 7))
                        .build(), 1706.4846416382252) //beginDate is ignored because endDate is not specified
        );
    }

    @ParameterizedTest
    @MethodSource("createArgumentsForTestCalculateRangeOfDates")
    void testCalculateRangeOfDates(DataForCalculation data, double expected) {
        assertEquals(expected, service.calculate(data));
    }

    private static Stream<Arguments> createArgumentsForTestCalculateRangeOfDatesException() {
        return Stream.of(
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .beginDate(LocalDate.of(2023, 3, 8)) //1 vacation day is a holiday day = 0 vacation days
                        .endDate(LocalDate.of(2023, 3, 8))
                        .build(), "Amount of vacation days must be positive"),
                Arguments.of(DataForCalculation.builder() // avgSalary is not specified
                        .beginDate(LocalDate.of(2023, 3, 7))
                        .endDate(LocalDate.of(2023, 3, 7))
                        .build(), "Average salary must be positive"),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000) // vacation period and amountOfDays is not specified
                        .build(), "Amount of vacation days must be positive"),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(5000)
                        .beginDate(LocalDate.of(2023, 3, 10)) //end day is before begin date
                        .endDate(LocalDate.of(2023, 3, 5))
                        .build(), "Begin date must be before end date"),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(-1)
                        .amountOfDays(5)
                        .build(), "Average salary must be positive"),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .amountOfDays(-1)
                        .build(), "Amount of vacation days must be positive"),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(-1)
                        .amountOfDays(-1)
                        .build(), "Amount of vacation days must be positive"),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(0)
                        .amountOfDays(5)
                        .build(), "Average salary must be positive"),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(1000)
                        .amountOfDays(0)
                        .build(), "Amount of vacation days must be positive"),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(0)
                        .amountOfDays(0)
                        .build(), "Amount of vacation days must be positive")
        );
    }

    @ParameterizedTest
    @MethodSource("createArgumentsForTestCalculateRangeOfDatesException")
    void testRangeOfDatesIllegalArgumentException(DataForCalculation data, String expectedMessage) {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> service.calculate(data));
        assertEquals(expectedMessage, thrown.getMessage());
    }
}