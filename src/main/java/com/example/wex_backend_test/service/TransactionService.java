package com.example.wex_backend_test.service;

import com.example.wex_backend_test.controller.TransactionDTO;
import com.example.wex_backend_test.model.Transaction;
import com.example.wex_backend_test.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionRepository repository;
    private TransactionMapper mapper;
    private ReportingRateService reportingRateService;

    public Transaction save(TransactionDTO transaction) {
        return repository.save(mapper.dtoToEntity(transaction));
    }

    public ConvertedTransaction getByCurrency(String id, String currency) {
        Transaction transaction = repository.findById(id).orElseThrow();
        BigDecimal exchangeRate = reportingRateService.getExchangeRate(transaction.getTransactionDate(), currency);
        return convertTransaction(transaction, exchangeRate);
    }

    private ConvertedTransaction convertTransaction(Transaction transaction, BigDecimal exchangeRate) {
        ConvertedTransaction convertedTransaction = new ConvertedTransaction();
        convertedTransaction.setId(transaction.getId());
        convertedTransaction.setDescription(transaction.getDescription());
        convertedTransaction.setTransactionDate(transaction.getTransactionDate());
        convertedTransaction.setExchangeRate(exchangeRate);
        convertedTransaction.setUsDollarPurchase(transaction.getPurchaseAmount());
        convertedTransaction.setConvertedAmount(getAmountMultiplied(exchangeRate, transaction.getPurchaseAmount()));
        return convertedTransaction;
    }

    private static BigDecimal getAmountMultiplied(BigDecimal exchangeRate, BigDecimal purchaseAmount) {
        return purchaseAmount.multiply(exchangeRate);
    }
}
