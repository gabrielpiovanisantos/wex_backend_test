package com.example.wex_backend_test.util;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReportingRate {

    private LocalDate recordDate;
    private String country;
    private String currency;
    private String countryCurrencyDesc;
    private BigDecimal exchangeRate;
    private LocalDate effectiveDate;
    private Integer srcLineNbr;
}
