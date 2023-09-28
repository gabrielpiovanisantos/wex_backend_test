package com.example.wex_backend_test.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Setter
@Data
public class ErrorMessage {

    public HttpStatusCode status;
    public String error;
}
