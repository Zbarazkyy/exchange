package com.example.exchange.service.mapper;

import com.example.exchange.dto.apiexchangerate.ConversionRates;
import com.example.exchange.dto.apiexchangerate.ExchangeRateApiRoot;
import com.example.exchange.dto.exchangeratehost.ExchangeRateHostRoot;
import com.example.exchange.dto.model.CurrencyResponseDto;
import com.example.exchange.model.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {
    public Currency fromERApiToModal(ExchangeRateApiRoot root, String conversionCurrency) {
        Currency currency = new Currency();
        currency.setBaseCurrency(root.getBase_code());
        currency.setConversionCurrency(conversionCurrency);
        currency.setConversionRates(
                getConversionRates(root.getConversion_rates(), conversionCurrency));
        currency.setDate(LocalDate.now());
        return currency;
    }

    public Currency fromERHostToModal(ExchangeRateHostRoot root, String conversionCurrency) {
        Currency currency = new Currency();
        currency.setBaseCurrency(root.getQuery().getFrom());
        currency.setConversionCurrency(conversionCurrency);
        currency.setConversionRates(root.getResult());
        currency.setDate(LocalDate.now());
        return currency;
    }

    public CurrencyResponseDto fromModalToDto(Currency currency) {
        return new CurrencyResponseDto(currency.getBaseCurrency(), currency.getConversionCurrency(),
                currency.getConversionRates(), currency.getDate());
    }

    public List<CurrencyResponseDto> fromModelListToDtoList(List<Currency> currencyList) {
        return currencyList.stream()
                .map(c -> new CurrencyResponseDto(c.getBaseCurrency(),
                        c.getConversionCurrency(), c.getConversionRates(), c.getDate()))
                .collect(Collectors.toList());
    }

    private double getConversionRates(ConversionRates conversionRates, String conversionCurrency) {
        Map<String, Double> map = new ObjectMapper().convertValue(conversionRates, Map.class);
        return map.get(conversionCurrency.toUpperCase());
    }
}
