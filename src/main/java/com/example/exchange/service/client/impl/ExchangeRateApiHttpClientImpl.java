package com.example.exchange.service.client.impl;

import static com.example.exchange.util.Util.CLIENT_NAME_ERAPI;

import com.example.exchange.dto.apiexchangerate.AvailableCurrency;
import com.example.exchange.dto.apiexchangerate.ExchangeRateApiRoot;
import com.example.exchange.exception.ClientERApiException;
import com.example.exchange.service.client.HttpClient;
import com.example.exchange.util.Util;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExchangeRateApiHttpClientImpl
        implements HttpClient<ExchangeRateApiRoot, AvailableCurrency> {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ExchangeRateApiRoot getCourse(String baseCurrency, String conversionCurrency) {
        HttpGet request = new HttpGet(Util.URL_ACTUAL_COURSE_EXCHANGERATE_API + baseCurrency);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            log.debug("Received a reply with course data from exchangerate-api.com");
            HttpEntity entity = response.getEntity();
            return objectMapper.readValue(entity.getContent(), ExchangeRateApiRoot.class);
        } catch (IOException e) {
            log.error("Error when receiving and working with data from exchangerate-api.com "
                    + "on currencies " + baseCurrency + " to " + conversionCurrency);
            throw new ClientERApiException("Can't get currency from Free Currency API. ", e);
        }
    }

    @Override
    public AvailableCurrency getAvailableCurrency() {
        HttpGet request = new HttpGet(Util.URL_AVAILABLE_CURRENCY);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            log.debug("Received a response with data available currency codes "
                    + "from exchangerate-api.com");
            HttpEntity entity = response.getEntity();
            return objectMapper.readValue(entity.getContent(), AvailableCurrency.class);
        } catch (IOException e) {
            log.error("Error when receiving and working with available currency codes ");
            throw new ClientERApiException("Can't get currency from Free Currency API. ", e);
        }
    }

    @Override
    public boolean isApplicable(String clientName) {
        return CLIENT_NAME_ERAPI.equals(clientName);
    }
}
