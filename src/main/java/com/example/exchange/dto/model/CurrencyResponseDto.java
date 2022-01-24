package com.example.exchange.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyResponseDto {
    @JsonProperty("base_currency")
    private String baseCurrency;
    @JsonProperty("conversion_currency")
    private String conversionCurrency;
    @JsonProperty("conversion_rates")
    private double conversionRates;
    @JsonProperty("date")
    private LocalDate date;
}
