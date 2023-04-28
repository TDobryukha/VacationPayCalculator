package com.example.VacationPayCalculator.confuguration;

import com.example.VacationPayCalculator.CalendarDayUtils;
import com.example.VacationPayCalculator.model.CalendarDay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class VacationPayCalculatorConfiguration {

    @Bean
    public Map<LocalDate, Boolean> getListOfHolidays() {
        List<CalendarDay> holidays = new ArrayList<>();
        try {
            holidays = CalendarDayUtils.loadHolidays("/calendar_23_24.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return holidays.stream()
                .collect(Collectors.toMap(CalendarDay::getDate, CalendarDay::isHoliday));
    }

}
