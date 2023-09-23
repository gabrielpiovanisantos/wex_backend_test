package com.example.wex_backend_test.service;

import com.example.wex_backend_test.controller.TransactionDTO;
import com.example.wex_backend_test.model.Transaction;
import com.example.wex_backend_test.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionRepository repository;
    private TransactionMapper mapper;

    public Transaction save(TransactionDTO transaction) {
        return repository.save(mapper.dtoToEntity(transaction));
    }
}
