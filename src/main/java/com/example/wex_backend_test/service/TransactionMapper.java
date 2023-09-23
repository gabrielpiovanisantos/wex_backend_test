package com.example.wex_backend_test.service;

import com.example.wex_backend_test.controller.TransactionDTO;
import com.example.wex_backend_test.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction dtoToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setPurchaseAmount(transactionDTO.getPurchaseAmount());
        return transaction;
    }
}
