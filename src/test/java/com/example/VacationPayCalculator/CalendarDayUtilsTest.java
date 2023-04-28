package com.example.VacationPayCalculator;

import com.example.VacationPayCalculator.model.CalendarDay;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalendarDayUtilsTest {
    @ParameterizedTest
    @CsvSource("/calendar_23_24.json, 39")
    void load(String fileName, int excepted) throws IOException {
        List<CalendarDay> holidays = CalendarDayUtils.loadHolidays(fileName);
        assertEquals(excepted, holidays.size());
    }
    @ParameterizedTest
    @CsvSource({"/calendar.json",
            "/emptyFile.json"})
    void loadException(String fileName) {
         assertThrows(IOException.class, () -> CalendarDayUtils.loadHolidays(fileName));
    }
}