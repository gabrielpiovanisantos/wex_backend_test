package com.example.wex_backend_test.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReportingRate {

    @JsonProperty(value = "record_date")
    private LocalDate recordDate;

    private String country;
    private String currency;
    @JsonProperty(value = "exchange_rate")
    private BigDecimal exchangeRate;
    @JsonProperty(value = "effective_date")
    private LocalDate effectiveDate;

}
