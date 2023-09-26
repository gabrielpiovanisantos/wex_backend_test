package com.example.wex_backend_test.service;

import com.example.wex_backend_test.model.Transaction;
import com.example.wex_backend_test.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceTest {


    private TransactionService transactionService;
    private final TransactionRepository repository = Mockito.mock(TransactionRepository.class);
    private final TransactionMapper transactionMapper = new TransactionMapper();
    private final ReportingRateService reportingRateService = Mockito.mock(ReportingRateService.class);

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(repository,transactionMapper,reportingRateService);
    }

    @Test
    void GivenTransactionAndExchangeRate_WhenReceivedIdAndCurrency_ThenShouldCreateAConvertedTransaction() {
        var transaction = new Transaction();
        transaction.setPurchaseAmount(BigDecimal.ONE);
        BigDecimal exchangeRate = TEN;

        when(repository.findById(any())).thenReturn(Optional.of(transaction));
        when(reportingRateService.getExchangeRate(any(),any())).thenReturn(exchangeRate);

        ConvertedTransaction convertedTransaction = transactionService.getByCurrency("test", "afghani");
        assertThat(convertedTransaction.getTransactionDate()).isEqualTo(transaction.getTransactionDate());
        assertThat(convertedTransaction.getExchangeRate()).isEqualTo(exchangeRate);
        assertThat(convertedTransaction.getId()).isEqualTo(transaction.getId());
        assertThat(convertedTransaction.getConvertedAmount()).
                isEqualTo(transaction.getPurchaseAmount().multiply(exchangeRate).setScale(2, RoundingMode.CEILING));
        assertThat(convertedTransaction.getUsDollarPurchase()).isEqualTo(transaction.getPurchaseAmount());
    }

}