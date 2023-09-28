package com.example.wex_backend_test.controller;

import com.example.wex_backend_test.service.ConvertedTransaction;
import com.example.wex_backend_test.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public TransactionDTO save(@RequestBody @Valid TransactionDTO transaction) {
        return service.save(transaction);
    }

    @GetMapping("/{id}/{currency}")
    public ConvertedTransaction getByCurrency(@PathVariable String id, @PathVariable String currency) {
        return service.getByCurrency(id, currency);
    }

    @GetMapping
    public List<TransactionDTO> findAll() {
        return service.findAll();
    }


}
