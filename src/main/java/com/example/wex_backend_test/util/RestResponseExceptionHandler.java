package com.example.wex_backend_test.util;


import com.example.wex_backend_test.util.exception.NoExchangeRateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatusCode.valueOf;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorMessage(status, ex.getMessage()),
                headers, status, request);
    }

    @ExceptionHandler(NoExchangeRateException.class)
    protected ResponseEntity<Object> handleNoExchangeRateFound(NoExchangeRateException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorMessage(valueOf(BAD_REQUEST.value()), ex.getMessage()), new HttpHeaders(), valueOf(BAD_REQUEST.value()), request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElement(NoSuchElementException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorMessage(valueOf(BAD_REQUEST.value()), "No element found"), new HttpHeaders(), valueOf(BAD_REQUEST.value()), request);
    }

}
