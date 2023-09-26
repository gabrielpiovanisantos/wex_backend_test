package com.example.wex_backend_test.controller;

import com.example.wex_backend_test.service.ConvertedTransaction;
import com.example.wex_backend_test.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("transactions")
@AllArgsConstructor
public class TransactionController {

    private TransactionService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public TransactionDTO save(@RequestBody @Valid TransactionDTO transaction) {
        return service.save(transaction);
    }

    @GetMapping()
    public ConvertedTransaction getByCurrency(@RequestParam String id, @RequestParam String currency) {
        return service.getByCurrency(id, currency);
    }

}
