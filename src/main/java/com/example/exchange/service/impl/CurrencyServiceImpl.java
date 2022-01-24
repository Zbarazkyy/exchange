package com.example.exchange.service.impl;

import static com.example.exchange.util.Util.CLIENT_NAME_ERAPI;
import static com.example.exchange.util.Util.CLIENT_NAME_ERHOST;

import com.example.exchange.dto.apiexchangerate.AvailableCurrency;
import com.example.exchange.dto.apiexchangerate.ExchangeRateApiRoot;
import com.example.exchange.dto.exchangeratehost.ExchangeRateHostRoot;
import com.example.exchange.model.Currency;
import com.example.exchange.repository.CurrencyRepository;
import com.example.exchange.service.CurrencyService;
import com.example.exchange.service.client.ClientHandlerStrategy;
import com.example.exchange.service.mapper.CurrencyMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private ClientHandlerStrategy clientStrategy;
    private CurrencyRepository repository;
    private CurrencyMapper mapper;

    public CurrencyServiceImpl(ClientHandlerStrategy clientStrategy,
                               CurrencyRepository repository, CurrencyMapper mapper) {
        this.clientStrategy = clientStrategy;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Currency getActualExchangeRate(String baseCurrency, String conversionCurrency) {
        Currency currencyFromERApi = mapper.fromERApiToModal((ExchangeRateApiRoot) clientStrategy
                .getClient(CLIENT_NAME_ERAPI)
                .getCourse(baseCurrency, conversionCurrency), conversionCurrency);
        Currency currencyFromERHost = mapper.fromERHostToModal((ExchangeRateHostRoot) clientStrategy
                .getClient(CLIENT_NAME_ERHOST)
                .getCourse(baseCurrency, conversionCurrency), conversionCurrency);
        Currency bestCourse = compareCourses(currencyFromERHost, currencyFromERApi);
        Optional<Currency> currencyFromBD = repository
                .findByDataCurrency(
                        bestCourse.getDate(),
                        bestCourse.getBaseCurrency(),
                        bestCourse.getConversionCurrency());
        return currencyFromBD.orElseGet(() -> repository.save(bestCourse));
    }

    @Override
    public AvailableCurrency getAvailableCurrency() {
        return (AvailableCurrency) clientStrategy
                .getClient(CLIENT_NAME_ERAPI)
                .getAvailableCurrency();
    }

    @Override
    public List<Currency> getHistoryFromDate(LocalDate date, String currency) {
        return repository.findAllByData(date, currency);
    }

    private Currency compareCourses(Currency currencyERHost, Currency currencyERApi) {
        if (currencyERHost.getConversionRates() > currencyERApi.getConversionRates()) {
            log.debug("api.exchangerate.host has the best course");
            return currencyERHost;
        }
        log.debug("exchangerate-api.com has the best course");
        return currencyERApi;
    }
}
