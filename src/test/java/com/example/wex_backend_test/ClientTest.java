package com.example.wex_backend_test;

import com.example.wex_backend_test.infra.Body;
import com.example.wex_backend_test.infra.HttpClientApp;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClientTest {

    @Autowired
    HttpClientApp httpClientApp;



    @Test
    @SneakyThrows
    void testClient() {
        String url = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange?filter=currency:eq:Afghani";
        HttpResponse<String> response = httpClientApp.invoke(URI.create(url));
        System.out.println(response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Body body = objectMapper.readValue(response.body(), Body.class);
        assertThat(body.getData().size()).isNotZero();
        assertThat(body.getData().get(0).getCountry()).isEqualTo("Afghanistan");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
