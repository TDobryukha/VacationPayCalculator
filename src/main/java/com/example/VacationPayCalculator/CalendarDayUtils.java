package com.example.VacationPayCalculator;

import com.example.VacationPayCalculator.model.CalendarDay;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CalendarDayUtils {
    public static List<CalendarDay> loadHolidays(String fileName) throws IOException {
        try (InputStream inputStream = VacationPayCalculatorApplication.class.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new FileNotFoundException(fileName);
            }
            return new ObjectMapper().findAndRegisterModules().readValue(inputStream, new TypeReference<>() {});
        }
    }
}
