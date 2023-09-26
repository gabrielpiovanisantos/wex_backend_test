package com.example.wex_backend_test.controller;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Validated
public class TransactionDTO {

    private String transactionId;
    @Size(max = 50)
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate transactionDate;
    @Digits(integer=5, fraction=2)
    private BigDecimal purchaseAmount;

}
