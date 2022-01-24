package com.example.exchange.controller;

import static com.example.exchange.util.PatternUtil.DATE_PATTERN;

import com.example.exchange.dto.apiexchangerate.AvailableCurrency;
import com.example.exchange.dto.model.CurrencyResponseDto;
import com.example.exchange.model.Currency;
import com.example.exchange.service.CurrencyService;
import com.example.exchange.service.mapper.CurrencyMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/exchange")
public class CurrencyController {
    private CurrencyService currencyService;
    private CurrencyMapper currencyMapper;

    public CurrencyController(CurrencyService currencyService, CurrencyMapper currencyMapper) {
        this.currencyService = currencyService;
        this.currencyMapper = currencyMapper;
    }

    @GetMapping("/actual")
    public ResponseEntity<CurrencyResponseDto> getActualExchangeRate(
            @RequestParam String baseCurrency, @RequestParam String conversionCurrency) {
        log.trace("Received currencies to get the course from " + baseCurrency
                + " to " + conversionCurrency + ". ");
        Currency actualExchangeRate = currencyService
                .getActualExchangeRate(baseCurrency, conversionCurrency);
        return ResponseEntity.ok(currencyMapper.fromModalToDto(actualExchangeRate));
    }

    @GetMapping("/available")
    public ResponseEntity<AvailableCurrency> getAvailableCurrency() {
        log.trace("Received a request to receive available currency codes. ");
        return ResponseEntity.ok(currencyService.getAvailableCurrency());
    }

    @PostMapping("/history")
    public ResponseEntity<List<CurrencyResponseDto>> getHistoryFromDate(
            @DateTimeFormat(pattern = DATE_PATTERN)
                    LocalDate date, @RequestParam String currency) {
        log.trace("Received request for currency receipt history from "
                + date.toString() + " dates by " + currency + " currency. ");
        List<CurrencyResponseDto> responseDtos = currencyMapper
                .fromModelListToDtoList(currencyService.getHistoryFromDate(date, currency));
        return ResponseEntity.ok(responseDtos);
    }
}
