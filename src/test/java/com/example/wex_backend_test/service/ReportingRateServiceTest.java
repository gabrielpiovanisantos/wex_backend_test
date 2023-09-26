package com.example.wex_backend_test.service;

import com.example.wex_backend_test.util.Body;
import com.example.wex_backend_test.util.HttpClientApp;
import com.example.wex_backend_test.util.ReportingRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.net.ssl.SSLSession;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ReportingRateServiceTest {

    public static final String NO_EXCHANGE_RATE_FOUND_WITHIN_SIX_MONTHS_FOR_THIS_CURRENCY = "No exchange rate found within six months for this currency";
    private ReportingRateService reportingRateService;
    private final HttpClientApp httpClientApp = Mockito.mock(HttpClientApp.class);
    private final ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
    private HttpResponse<String> response;
    private Body body;
    private String currency;
    private LocalDate now;
    private BigDecimal ten;

    @BeforeEach
    void setUp() {
        reportingRateService = new ReportingRateService(httpClientApp, objectMapper);
    }

    @Test
    @SneakyThrows
    void GivenTheReportRateListFromTheAPI_WhenReceivingTransactionDateAndCurrency_ThenMustReturnTheProperExchangeRate() {
        initializeScenario(0);

        when(httpClientApp.invoke(any())).thenReturn(response);
        when(objectMapper.readValue((String) any(), eq(Body.class))).thenReturn(body);

        BigDecimal exchangeRate = reportingRateService.getExchangeRate(now, currency);
        assertThat(exchangeRate).isEqualTo(ten);
    }

    @Test
    @SneakyThrows
    void GivenTheReportListFromTheAPI_WhenReceivingDifferentTransactionDate_ThenMustReturnTheExchangeRateFromSixMonthsIn() {
        initializeScenario(5);

        when(httpClientApp.invoke(any())).thenReturn(response);
        when(objectMapper.readValue((String) any(), eq(Body.class))).thenReturn(body);

        BigDecimal exchangeRate = reportingRateService.getExchangeRate(now, currency);

        assertThat(exchangeRate).isEqualTo(ten);
    }

    @Test
    @SneakyThrows
    void GivenReportRatesFromAPI_WhenNoExchangeRateWithinSixMonthsForTheCurrency_TheMustThrowProperException() {
        initializeScenario(7);

        when(httpClientApp.invoke(any())).thenReturn(response);
        when(objectMapper.readValue((String) any(), eq(Body.class))).thenReturn(body);

        Throwable exception = assertThrows(NoExchangeRateException.class, () -> reportingRateService.getExchangeRate(now, currency));
        assertThat(exception.getMessage()).isEqualTo(NO_EXCHANGE_RATE_FOUND_WITHIN_SIX_MONTHS_FOR_THIS_CURRENCY);
    }

    private void initializeScenario(int monthsToSubtract) {
        response = getResponse();
        body = new Body();
        List<ReportingRate> data = new ArrayList<>();
        currency = "Afghani";
        now = LocalDate.now();
        ReportingRate e = new ReportingRate();
        e.setCurrency(currency);
        e.setEffectiveDate(now.minusMonths(monthsToSubtract));
        ten = BigDecimal.TEN;
        e.setExchangeRate(ten);
        data.add(e);
        body.setData(data);
    }

    private static HttpResponse<String> getResponse() {
        return new HttpResponse<>() {
            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return null;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }
}