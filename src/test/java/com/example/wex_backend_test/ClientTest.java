package com.example.wex_backend_test;

import com.example.wex_backend_test.util.Body;
import com.example.wex_backend_test.util.HttpClientApp;
import com.example.wex_backend_test.util.ReportingRate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

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
        Body body = objectMapper.readValue(response.body(), Body.class);
        assertThat(body.getData().size()).isNotZero();
        assertThat(body.getData().get(0).getCountry()).isEqualTo("Afghanistan");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
