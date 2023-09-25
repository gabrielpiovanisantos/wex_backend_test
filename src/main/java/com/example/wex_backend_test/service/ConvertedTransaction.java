package com.example.wex_backend_test.service;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ConvertedTransaction {

    private String id;
    private String description;
    private LocalDate transactionDate;
    private BigDecimal usDollarPurchase;
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;

}
