package com.example.wex_backend_test.controller;

import com.example.wex_backend_test.model.Transaction;
import com.example.wex_backend_test.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("transactions")
@AllArgsConstructor
public class TransactionController {

    private TransactionService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Transaction save(@RequestBody TransactionDTO transaction) {
        return service.save(transaction);
    }


}
