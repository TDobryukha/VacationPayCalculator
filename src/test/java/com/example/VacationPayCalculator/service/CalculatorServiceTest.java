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
                        .build(), 1706.4846416382252),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10500.22)
                        .amountOfDays(5)
                        .build(), 1791.8464163822523),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(1.7976931348623157E308)
                        .amountOfDays(30)
                        .build(), "Infinity"),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(1.7976931348623157E308)
                        .amountOfDays(1)
                        .build(), 6.135471450042033E306),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .beginDate(LocalDate.of(2023, 4, 29))
                        .endDate(LocalDate.of(2023, 5, 2))
                        .build(), 341.29692832764505),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .beginDate(LocalDate.of(2022, 3, 5)) //сб 05.03.22 - раб. день, 06.03 - вс
                        .endDate(LocalDate.of(2022, 3, 9)) //07.03, 08.03 - празд
                        .build(), 682.5938566552901),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .beginDate(LocalDate.of(2023, 3, 5)) //ВС 05.03.22 - вых, 08.03 - празд.
                        .endDate(LocalDate.of(2023, 3, 9))
                        .build(), 1023.8907849829352),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .beginDate(LocalDate.of(2023, 3, 7)) //1 день отпуска
                        .endDate(LocalDate.of(2023, 3, 7))
                        .build(), 341.29692832764505),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000) // заданы кол-во дней и даты начала и конца
                        .amountOfDays(5)
                        .beginDate(LocalDate.of(2023, 3, 7))
                        .endDate(LocalDate.of(2023, 3, 7))
                        .build(), 341.29692832764505),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)  // заданы кол-во дней и дата конца
                        .amountOfDays(5)
                        .endDate(LocalDate.of(2023, 3, 7))
                        .build(), 1706.4846416382252),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)  // заданы кол-во дней и дата начала
                        .amountOfDays(5)
                        .beginDate(LocalDate.of(2023, 3, 7))
                        .build(), 1706.4846416382252)
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
                        .beginDate(LocalDate.of(2023, 3, 8)) //1 день отпуска выпал на праздник = 0 дней
                        .endDate(LocalDate.of(2023, 3, 8))
                        .build()),
                Arguments.of(DataForCalculation.builder() // не указана ЗП
                        .beginDate(LocalDate.of(2023, 3, 7)) //1 день отпуска
                        .endDate(LocalDate.of(2023, 3, 7))
                        .build()),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000) // не указаны ни кол-во дней отпуска, ни даты начала и конца
                        .build()),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(5000)
                        .beginDate(LocalDate.of(2023, 3, 10)) //дата начала больше даты конца
                        .endDate(LocalDate.of(2023, 3, 5))
                        .build()),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(-1)
                        .amountOfDays(5)
                        .build()),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(10000)
                        .amountOfDays(-1)
                        .build()),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(-1)
                        .amountOfDays(-1)
                        .build()),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(0)
                        .amountOfDays(5)
                        .build()),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(1000)
                        .amountOfDays(0)
                        .build()),
                Arguments.of(DataForCalculation.builder()
                        .avgSalary(0)
                        .amountOfDays(0)
                        .build())
        );
    }

    @ParameterizedTest
    @MethodSource("createArgumentsForTestCalculateRangeOfDatesException")
    void testRangeOfDatesIllegalArgumentException(DataForCalculation data) {
        assertThrows(IllegalArgumentException.class, () -> service.calculate(data));
    }
}