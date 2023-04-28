package com.example.VacationPayCalculator.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @ParameterizedTest()
    @CsvSource({"/calculate?avgSalary=10000&amountOfDays=100, OK",
            "/calculate?avgSalary=1.7976931348623157E308&amountOfDays=1, OK",
            "/calculate?avgSalary=1.7976931348623157E308&amountOfDays=2, OK",
            "/calculate?avgSalary=10000.55&amountOfDays=-1, BAD_REQUEST",
            "/calculate?avgSalary=10000.55&amountOfDays=0, BAD_REQUEST",
            "/calculate?avgSalary=10000.55, BAD_REQUEST",
            "/calculate?amountOfDays=100, BAD_REQUEST",
            "/calculate?avgSalary=-1&amountOfDays=5, BAD_REQUEST",
            "/calculate?avgSalary=0&amountOfDays=5, BAD_REQUEST",
            "/calculate?amountOfDays=100&beginDate=26.04.2023&endDate=02.05.2023, BAD_REQUEST",
            "/calculate?avgSalary=10000&amountOfDays=100&beginDate=26.04.2023&endDate=02.05.2023, OK",
            "/calculate?avgSalary=10000&beginDate=26.04.2023&endDate=02.05.2023, OK",
            "/calculate?avgSalary=10000&endDate=02.05.2023, BAD_REQUEST",
            "/calculate?avgSalary=10000&beginDate=26.04.2023, BAD_REQUEST",
            "/calculate?avgSalary=10000&amountOfDays=100&beginDate=26.04.2023, OK",
            "/calculate?avgSalary=10000&amountOfDays=100&endDate=02.05.2023, OK",
            "/calculate, BAD_REQUEST",
            "/calculate?avgSalary=&amountOfDays=100,BAD_REQUEST",
            "/calculate?avgSalary=&amountOfDays=100,BAD_REQUEST",
            "/calculate?avgSalary=1000&amountOfDays=, BAD_REQUEST",
            "/calculate?avgSalary=1000&beginDate=&endDate=, BAD_REQUEST"
    })
    public void Test(String url, HttpStatus statusExpected) {
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, null, String.class);
        assertEquals(statusExpected, response.getStatusCode());
    }

}