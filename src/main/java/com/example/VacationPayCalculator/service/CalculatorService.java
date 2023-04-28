package com.example.VacationPayCalculator.service;

import com.example.VacationPayCalculator.model.DataForCalculation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private static final double AVG_NUMBER_OF_DAYS_IN_MONTH = 29.3;
    private static final Set<DayOfWeek> WEEKEND = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private final Map<LocalDate, Boolean> holidayCalendar;

    public double calculate(DataForCalculation data) throws IllegalArgumentException {
        checkPreparedData(data);
        return data.getAvgSalary() / AVG_NUMBER_OF_DAYS_IN_MONTH * data.getAmountOfDays();
    }

    private void checkPreparedData(DataForCalculation data) throws IllegalArgumentException {
        if (data.getBeginDate() != null && data.getEndDate() != null) {
            if (data.getBeginDate().isAfter(data.getEndDate())) {
                throw new IllegalArgumentException("Begin date must be before end date");
            } else {
                data.setAmountOfDays(defineWorkingDays(data.getBeginDate(), data.getEndDate()));
            }
        }
        if (data.getAmountOfDays() <= 0 || data.getAvgSalary() <= 0) {
            throw new IllegalArgumentException("Average salary and amount of vacation days must be positive");
        }
    }

    private int defineWorkingDays(LocalDate beginDate, LocalDate endDate) {
        return (int) Stream.iterate(beginDate, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(beginDate, endDate) + 1)
                .filter(Predicate.not(this::isHoliday)) // убрали праздничные дни
                .filter(this::isWorkDay) //убрали только те СБ и ВС, которые не являются рабочими из-за переноса
                .count();
    }

    private boolean isHoliday(LocalDate date) {
        return holidayCalendar.getOrDefault(date, false);
    }

    private boolean isWorkDay(LocalDate date) {
        return !holidayCalendar.getOrDefault(date, true) || !WEEKEND.contains(date.getDayOfWeek());
    }
}

