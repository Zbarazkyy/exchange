package com.example.exchange.service;

import com.example.exchange.dto.apiexchangerate.AvailableCurrency;
import com.example.exchange.model.Currency;
import java.time.LocalDate;
import java.util.List;

public interface CurrencyService {
    Currency getActualExchangeRate(String baseCurrency, String conversionCurrency);

    AvailableCurrency getAvailableCurrency();

    List<Currency> getHistoryFromDate(LocalDate date, String currency);
}
