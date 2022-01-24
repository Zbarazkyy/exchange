package com.example.exchange.service.client;

public interface HttpClient<T, D> {
    T getCourse(String baseCurrency, String conversionCurrency);

    D getAvailableCurrency();

    boolean isApplicable(String clientName);
}
