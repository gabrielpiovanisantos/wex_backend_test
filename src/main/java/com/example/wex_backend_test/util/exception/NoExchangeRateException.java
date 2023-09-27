package com.example.wex_backend_test.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoExchangeRateException extends RuntimeException {

    public NoExchangeRateException(String s) {
        super(s);
    }
}
