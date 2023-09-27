package com.example.wex_backend_test.service;

import com.example.wex_backend_test.infra.Body;
import com.example.wex_backend_test.infra.HttpClientApp;
import com.example.wex_backend_test.infra.ReportingRate;
import com.example.wex_backend_test.util.exception.NoExchangeRateException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Objects;

import static java.net.URI.create;

@Service
@RequiredArgsConstructor
public class ReportingRateService {

    private static final String URISTRING = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange";
    public static final String NO_EXCHANGE_RATE_FOUND_WITHIN_SIX_MONTHS_FOR_THIS_CURRENCY = "No exchange rate found within six months for this currency";
    private final HttpClientApp httpClientApp;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public BigDecimal getExchangeRate(LocalDate transactionDate, String currency) {
        String currencyFilter = "?filter=currency:eq:";
        String completeURI = URISTRING + currencyFilter + currency;
        HttpResponse<String> response = httpClientApp.invoke(create(completeURI));
        Body body = objectMapper.readValue(response.body(), Body.class);
        ReportingRate reportingRate = body.getData().stream().
                filter(it -> Objects.equals(it.getCurrency(), currency)).
                filter(it -> it.getEffectiveDate().isEqual(transactionDate) || (!it.getEffectiveDate().isAfter(transactionDate) &&
                        it.getEffectiveDate().isAfter(LocalDate.now().minusMonths(6)))).
                findFirst().orElse(null);
        if (reportingRate != null) return reportingRate.getExchangeRate();
        throw new NoExchangeRateException(NO_EXCHANGE_RATE_FOUND_WITHIN_SIX_MONTHS_FOR_THIS_CURRENCY);
    }
}
