package com.example.wex_backend_test.service;

import com.example.wex_backend_test.util.Body;
import com.example.wex_backend_test.util.HttpClientApp;
import com.example.wex_backend_test.util.ReportingRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Objects;

import static java.net.URI.create;

@Service
@AllArgsConstructor
public class ReportingRateService {

    private static final String URISTRING = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange";
    private HttpClientApp httpClientApp;
    private ObjectMapper objectMapper;

    @SneakyThrows
    public BigDecimal getExchangeRate(LocalDate transactionDate, String currency) {
        String currencyFilter = "?filter=currency:eq:";
        String completeURI = URISTRING + currencyFilter + currency;
        HttpResponse<String> response = httpClientApp.invoke(create(completeURI));
        Body body = objectMapper.readValue(response.body(), Body.class);
        ReportingRate reportingRate = body.getData().stream().
                filter(it -> Objects.equals(it.getCurrency(), currency)).
                filter(it -> !it.getEffectiveDate().isBefore(transactionDate)).
                filter(it -> it.getEffectiveDate().isAfter(LocalDate.now().minusMonths(6))).
                findFirst().orElse(null);
        return reportingRate != null ? reportingRate.getExchangeRate() : null;
    }
}
