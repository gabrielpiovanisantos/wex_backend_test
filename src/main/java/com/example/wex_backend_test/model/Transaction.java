package com.example.wex_backend_test.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document
@ToString
@Getter
@Setter
public class Transaction {

    @Id
    private String id;
    private String description;
    private LocalDate transactionDate;
    private BigDecimal purchaseAmount;

}
